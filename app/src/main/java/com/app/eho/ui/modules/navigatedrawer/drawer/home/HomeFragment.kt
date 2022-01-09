package com.app.eho.ui.modules.navigatedrawer.drawer.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Criteria
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.eho.R
import com.app.eho.data.model.Ambulance
import com.app.eho.databinding.FragmentHomeBinding
import com.app.eho.ui.base.BaseFragment
import com.app.eho.ui.custom.AlertDialogUtil
import com.app.eho.utils.Utils
import com.app.eho.utils.location.GPSTracker
import com.app.eho.utils.location.LocationAddress
import com.app.eho.utils.log.LogUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.lang.String
import java.util.*

class HomeFragment : BaseFragment() {

    lateinit var viewModel: HomeViewModel
    lateinit var _binding: FragmentHomeBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding
    private var mContext: Context? = null
    private var isMapLoaded = false
    private val savedInstanceState: Bundle? = null

    private var currentLatitude = 0.0
    private var currentLongitude: Double = 0.0
    private val FixAddress: LatLng? = null
    private var googleMap: GoogleMap? = null

    private lateinit var adapter: AmbulanceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContext = activity
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        _binding.btnPickup.setOnClickListener(View.OnClickListener {
            /*_binding.constrainAddress.visibility= View.GONE
            _binding.constraintConfirmAdd.visibility=View.VISIBLE*/
            Toast.makeText(mContext, " Work in progress ", Toast.LENGTH_SHORT)
                .show()
        })
        _binding.btnConfirmPickupAdd.setOnClickListener {
            _binding.constrainAddress.visibility= View.VISIBLE
            _binding.constraintConfirmAdd.visibility=View.GONE
            _binding.tvAlertLabel.setText(resources.getString(R.string.map_pick_up_label))
            _binding.ivClose.visibility = View.VISIBLE
            if(adapter != null) {
                var ambulance = adapter.getSelectedItem()
                if (ambulance != null) {
                    Toast.makeText(mContext, " Selected : " + ambulance.name, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        _binding.tvAlertLabel.setText(resources.getString(R.string.map_choose_ambulance_label))
        _binding.constrainAddress.visibility= View.GONE
        _binding.constraintConfirmAdd.visibility=View.VISIBLE

        binding.ivClose.visibility = View.GONE
        _binding.ivClose.setOnClickListener {
            if(_binding.constrainAddress.visibility == View.VISIBLE){
                _binding.constrainAddress.visibility= View.GONE
                _binding.constraintConfirmAdd.visibility=View.VISIBLE
                _binding.ivClose.visibility = View.GONE
            }
        }

        showAmbulanceList()
        return root
    }

    override fun onStart() {
        super.onStart()
        loadMapOnScreen()
    }

    override fun onResume() {
        super.onResume()
        //called when activity will start interacting with the user.
        if (_binding.map != null) {
            _binding.map.onResume()
        }
        LogUtils.displayLog("Tag", " TrackingPreferenceFragment onResume: ")
    }

    override fun onPause() {
        super.onPause()
        //called when activity is not visible to the user.
        if (_binding.map != null) {
            _binding.map.onPause()
        }
        LogUtils.displayLog("Tag", " TrackingPreferenceFragment onPause: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (_binding.map != null) {
            _binding.map.onDestroy()
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        if (_binding.map != null) {
            _binding.map.onLowMemory()
        }
    }

    private fun loadMapOnScreen() {
        if (!isMapLoaded) {
            //Set MapView
            try {
                _binding.map.onCreate(savedInstanceState)
                _binding.map.onResume() // needed to get the map to display immediately
                try {
                    MapsInitializer.initialize(mContext!!)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                _binding.map.getMapAsync(OnMapReadyCallback { mMap ->
                    getMapReady(mMap)
                    isMapLoaded = true
                    LogUtils.displayLog("Tag", "Tracking preference open location on map ready ")
                })
            } catch (e: Exception) {
                Toast.makeText(
                    mContext,
                    resources.getString(R.string.location_service_disable),
                    Toast.LENGTH_SHORT
                ).show()
                requireActivity().onBackPressed()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getMapReady(gMap: GoogleMap) {
        try {
            LogUtils.displayLog("Tag", " TrackingPreferenceFragment get map ready: ")
            googleMap = gMap
            Dexter.withContext(mContext)
                .withPermissions(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .withListener(object : MultiplePermissionsListener {
                    @SuppressLint("MissingPermission")
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        if (report.areAllPermissionsGranted()) {

                            // For showing a move to my location button
                            googleMap!!.setMyLocationEnabled(true)

                            //getMyLocation() is deprecated
                            val lm =
                                mContext!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                            var location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                            if (location == null) {
                                val criteria = Criteria()
                                criteria.accuracy = Criteria.ACCURACY_COARSE
                                val provider = lm.getBestProvider(criteria, true)
                                location = lm.getLastKnownLocation(provider!!)
                            }
                            LogUtils.displayLog("TAG", "onMapReady: location1: $location")
                            if (location != null) {
                                val latitude = location.latitude
                                val longitude = location.longitude
                                currentLatitude = latitude
                                currentLongitude = longitude
                                //Display current marker
                                showPinLocation()
                            } else {
                                val IsGpsEnable: Boolean = Utils.checkGPSAvailable(mContext)
                                if (!IsGpsEnable) {
                                    AlertDialogUtil.GpsAlertMessage(mContext)
                                } else {
                                    val gpsTracker = GPSTracker(mContext)
                                    var strCurrentLatitude = ""
                                    var strCurrentLongitude = ""
                                    if (strCurrentLatitude.isEmpty()) {
                                        /*strCurrentLatitude = SharedPreferenceHelper.getMyStringPref(
                                            mContext,
                                            SharedPreferenceConstants.CurrentLatitude
                                        )
                                        strCurrentLongitude = SharedPreferenceHelper.getMyStringPref(
                                            mContext,
                                            SharedPreferenceConstants.CurrentLongitude
                                        )*/
                                    } else if (gpsTracker.getIsGPSTrackingEnabled()) {
                                        strCurrentLatitude =
                                            String.valueOf(gpsTracker.getLatitude())
                                        strCurrentLongitude =
                                            String.valueOf(gpsTracker.getLongitude())
                                    }
                                    if (!(Utils.isNull(strCurrentLatitude)
                                            .isEmpty() || strCurrentLatitude.equals(
                                            "0.0",
                                            ignoreCase = true
                                        ))
                                    ) {
                                        val latitude = strCurrentLatitude.toDouble()
                                        val longitude = strCurrentLongitude.toDouble()
                                        currentLatitude = latitude
                                        currentLongitude = longitude
                                        showPinLocation()
                                    }
                                }
                            }
                        }
                        googleMap!!.setOnCameraChangeListener(OnCameraChangeListener { cameraPosition ->
                            Log.d("Camera postion change" + "", cameraPosition.toString() + "")
                            val mCenterLatLong = cameraPosition.target
                            //googleMap!!.clear()
                            try {
                                currentLatitude = mCenterLatLong.latitude
                                currentLongitude = mCenterLatLong.longitude
                                val mLocation = Location("")
                                mLocation.latitude = mCenterLatLong.latitude
                                mLocation.longitude = mCenterLatLong.longitude
                                println("mLocation = " + mLocation.latitude + ":" + mLocation.longitude)
                                _binding.tvAddress.text=getaddress(mContext!!,currentLatitude.toFloat(),currentLongitude.toFloat())

                            } catch (e: java.lang.Exception) {
                            }
                        })
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: List<PermissionRequest>,
                        token: PermissionToken
                    ) {
                        token.continuePermissionRequest()
                    }
                }).check()
        } catch (e: Exception) {
            requireActivity().onBackPressed()
        }
        showNearByCabsOnMap()
        LogUtils.displayLog("Tag", " TrackingPreferenceFragment get map ready 2: ")
    }

    fun showNearByCabsOnMap() {
        try {
            Log.d("tag", " Method1: show near by cabs")
            // Add a marker in Sydney and move the camera
            val sydney = LatLng(-34.0, 151.0)
            googleMap!!.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            googleMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))

            // Add a marker in Sydney and move the camera
            var latLng1 = LatLng(-34.0, 151.0)
            var latLng2 = LatLng(28.439147000000002, 77.0944446)
            var latLng3 = LatLng(28.433147, 77.0952446)
            var latLng4 = LatLng(28.440547000000002, 77.1026446)

            var m1: MarkerOptions = MarkerOptions().position(latLng1).title("Marker in Sydney")
                .snippet("Population: 4,627,300").icon(
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_car)
                )
            var m2: MarkerOptions = MarkerOptions().position(latLng2).title("Marker in Group1")
                .snippet("Population: 4,627").icon(
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_car)
                )
            var m3: MarkerOptions = MarkerOptions().position(latLng3).title("Marker in Group2")
                .snippet("Population: 4,6").icon(
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_car)
                )
            var m4: MarkerOptions = MarkerOptions().position(latLng4).title("Marker in Group3")
                .snippet("Population: 4,627,3").icon(
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_car)
                )

            // mMap.addMarker(m1)
            googleMap!!.addMarker(m2)
            googleMap!!.addMarker(m3)
            googleMap!!.addMarker(m4)

            val builder = LatLngBounds.builder()
            //builder.include(latLng1)
            builder.include(latLng2)
            builder.include(latLng3)
            builder.include(latLng4)
            //currentLatLog?.let { builder.include(it) }

            //create a bound
            val bounds = builder.build()

            /*//set a 30 pixels padding from the edge of the screen
            googleMap!!.setOnMapLoadedCallback(GoogleMap.OnMapLoadedCallback {
                //move and animate the camera
                googleMap!!.moveCamera(
                    CameraUpdateFactory.newLatLngBounds(
                        bounds,
                        400
                    )
                )

                //animate camera by providing zoom and duration args, callBack set to null
                // mMap.animateCamera(CameraUpdateFactory.zoomTo(2f), 2000, null)
            })*/

            showPinLocation()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun showPinLocation() {
        try {
            //Place current location marker
            val markerOptions = MarkerOptions()
            markerOptions.position(LatLng(currentLatitude, currentLongitude))
            markerOptions.title("Current Position")
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            googleMap!!.addMarker(markerOptions)
            googleMap!!.animateCamera(
                CameraUpdateFactory.newLatLng(
                    LatLng(
                        currentLatitude,
                        currentLongitude
                    )
                )
            )
            // For zooming automatically to the location of the marker
            val cameraPosition =
                CameraPosition.Builder().target(LatLng(currentLatitude, currentLongitude)).zoom(17f)
                    .build()
            googleMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            binding.tvAddress.text = getaddress(
                mContext!!,
                currentLatitude.toFloat(),
                currentLongitude.toFloat()
            ).toString()

            showCurrentAddressInPickUp()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun showCurrentAddressInPickUp() {
        val locationAddress = LocationAddress()

        locationAddress.getAddressFromLocation(
            currentLatitude,
            currentLongitude,
            mContext!!,
            GeoCodeHandler()
        )
    }

    //TODO get Lat and Long to get Address
    private fun getaddress(context: Context, latitude: Float, longitude: Float): kotlin.String {
        val gcd = Geocoder(context, Locale.getDefault())
        val addresses = gcd.getFromLocation(latitude.toDouble(), longitude.toDouble(), 1)
        if (addresses.size > 0) {
            val locality = addresses[0].locality
            val getAddressLine0 = addresses[0].getAddressLine(0)
            if (getAddressLine0 != null) {
                val strs = getAddressLine0.split(",").toTypedArray()
                if (strs.count() == 1) {
                    if (locality != null && locality != "") {
                        return "$locality, ${strs[0]}"
                    }
                }
                for (str in strs) {
                    var tempStr = str.replace("", "")
                    if (tempStr != str) {
                        tempStr = tempStr.trim()
                        if (locality != null && locality != "" && locality != tempStr) {
                            return "$locality, $tempStr"
                        }
                    }
                }
            }
            /* if (locality != null) {
                 return locality
             }*/
            return getAddressLine0
        }
        return ""
    }

    internal inner class GeoCodeHandler : Handler() {
        override fun handleMessage(message: Message) {
            var locationAddress: kotlin.String = ""
            when (message.what) {
                1 -> {
                    val bundle = message.data
                    locationAddress = bundle.getString("address").toString()
                }
            }

            binding.pickUpTextView.text = locationAddress
        }
    }

    fun showAmbulanceList(){

        // this creates a vertical layout Manager
        binding.recyleviewList.layoutManager = LinearLayoutManager(mContext)
        // ArrayList of class ItemsViewModel
        val data =  ArrayList<Ambulance>()
        data.add(Ambulance(1, "Zydus hospital", "₹ 300.32", "7:13pm 11 min away"))
        data.add(Ambulance(2, "Sterling hospital", "₹ 150", "7:13pm 11 min away"))
        data.add(Ambulance(3, "Sola hospital", "₹ 100", "7:13pm 11 min away"))
        // This will pass the ArrayList to our Adapter
        adapter = AmbulanceAdapter(data, AmbulanceAdapter.OnClickListener { photo ->
            Toast.makeText(mContext, "${photo.name}", Toast.LENGTH_SHORT).show() })
        // Setting the Adapter with the recyclerview
        binding.recyleviewList.adapter = adapter

        /*adapter = AmbulanceAdapter(AmbulanceAdapter.OnClickListener { photo ->
            Toast.makeText(mContext, "${photo.name}", Toast.LENGTH_SHORT).show() })

        adapter.su
        viewModel.response.observe(this, Observer { meme ->
            val list = meme.data?.memes
            adapter.submitList(list)
            binding.recyclerView.adapter = adapter
        })*/


    }



}