package com.app.eho

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import android.os.Handler
import android.os.Message

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.app.eho.databinding.ActivityMapsBinding
import com.app.eho.ui.MainActivity
import com.app.eho.utils.location.LocationAddress
import com.app.eho.utils.network.NetUtils
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.IOException
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener,
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    internal lateinit var mLastLocation: Location
    internal var mCurrLocationMarker: Marker? = null
    internal lateinit var mLocationRequest: LocationRequest
    private var mGoogleApiClient: GoogleApiClient? = null
    private var myLocation: Location? = null
    var currentLatLog : LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        writeLocation()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //Check M permission
        Dexter.withContext(this)
            .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            .withListener(object : MultiplePermissionsListener {
                @SuppressLint("MissingPermission")
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        buildGoogleApiClient()
                        mMap!!.isMyLocationEnabled = true
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).check()

        showNearByCabsOnMap()
    }

    @Synchronized
    protected fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this, 0, this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API).build()
        mGoogleApiClient!!.connect()
    }

    override fun onConnected(bundle: Bundle?) {
        getMyLocation(false)
        mLocationRequest = LocationRequest()
        mLocationRequest.interval = 1000
        mLocationRequest.fastestInterval = 1000
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.getFusedLocationProviderClient(this)
        }
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun onLocationChanged(location: Location) {
        Log.d("tag"," Method: onLocationChanged")
        mLastLocation = location
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker!!.remove()
        }
        //Place current location marker
        currentLatLog = LatLng(location.latitude, location.longitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(currentLatLog!!)
        markerOptions.title("Current Position")
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        mCurrLocationMarker = mMap!!.addMarker(markerOptions)
        Log.d("Tag"," current location : lat - "+location.latitude+ " long: "+location.longitude)

        showNearByCabsOnMap()

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.getFusedLocationProviderClient(this)
        }

        showCurrentAddressInPickUp()

    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }

    fun showNearByCabsOnMap(){
        try {
            Log.d("tag"," Method1: show near by cabs")
            // Add a marker in Sydney and move the camera
            val sydney = LatLng(-34.0, 151.0)
            mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

            // Add a marker in Sydney and move the camera
            var latLng1 = LatLng(-34.0, 151.0)
            var latLng2 = LatLng(28.439147000000002, 77.0944446)
            var latLng3 = LatLng(28.433147, 77.0952446)
            var latLng4 = LatLng(28.440547000000002, 77.1026446)

            var m1 : MarkerOptions = MarkerOptions().position(latLng1).title("Marker in Sydney").snippet("Population: 4,627,300").icon(
                BitmapDescriptorFactory.fromResource(R.drawable.ic_car))
            var m2 : MarkerOptions = MarkerOptions().position(latLng2).title("Marker in Group1").snippet("Population: 4,627").icon(
                BitmapDescriptorFactory.fromResource(R.drawable.ic_car))
            var m3 : MarkerOptions = MarkerOptions().position(latLng3).title("Marker in Group2").snippet("Population: 4,6").icon(
                BitmapDescriptorFactory.fromResource(R.drawable.ic_car))
            var m4 : MarkerOptions = MarkerOptions().position(latLng4).title("Marker in Group3").snippet("Population: 4,627,3").icon(
                BitmapDescriptorFactory.fromResource(R.drawable.ic_car))

            // mMap.addMarker(m1)
            mMap.addMarker(m2)
            mMap.addMarker(m3)
            mMap.addMarker(m4)

            val builder = LatLngBounds.builder()
            //builder.include(latLng1)
            builder.include(latLng2)
            builder.include(latLng3)
            builder.include(latLng4)
            currentLatLog?.let { builder.include(it) }

            //create a bound
            val bounds = builder.build()

            //set a 30 pixels padding from the edge of the screen
            mMap.setOnMapLoadedCallback(GoogleMap.OnMapLoadedCallback {
                //move and animate the camera
                mMap.moveCamera(
                    CameraUpdateFactory.newLatLngBounds(
                        bounds,
                        400
                    )
                )

                //animate camera by providing zoom and duration args, callBack set to null
                // mMap.animateCamera(CameraUpdateFactory.zoomTo(2f), 2000, null)
            })
        }catch (e:java.lang.Exception){
            e.printStackTrace()
        }
    }

    fun getMyLocation(isLocationEnable: Boolean) {
        try {
            if (mGoogleApiClient != null) {
                if (mGoogleApiClient!!.isConnected()) {
                    val permissionLocation = ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                        val minute = 2 * (60 * 1000) //60 second - 1*60 = 1 minute
                        myLocation =
                            LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient!!)
                        val locationRequest = LocationRequest()
                        locationRequest.interval = minute.toLong()
                        locationRequest.fastestInterval = minute.toLong()
                        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                        val builder = LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest)
                        builder.setAlwaysShow(true)
                        LocationServices.FusedLocationApi
                            .requestLocationUpdates(mGoogleApiClient!!, locationRequest, this)
                        val result = LocationServices.SettingsApi
                            .checkLocationSettings(mGoogleApiClient!!, builder.build())
                        result.setResultCallback { result ->
                            val status = result.status
                            when (status.statusCode) {
                                LocationSettingsStatusCodes.SUCCESS -> {
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    /*val permissionLocation = ContextCompat.checkSelfPermission(
                                        this@DashboardActivity,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    )
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        myLocation =
                                            LocationServices.FusedLocationApi.getLastLocation(
                                                googleApiClient
                                            )
                                    }*/
                                }
                                LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->                                         // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        if (isLocationEnable) {
                                            /*val homeInfo: HomeInfo =
                                                AppWidgetUtil.getAppWidgetDateInSharedPreference(
                                                    mContext
                                                )
                                            if (homeInfo != null &&
                                                homeInfo.isNextShiftHasRecording() &&
                                                homeInfo.getRecordTimeStatus()
                                                    .equalsIgnoreCase(Constants.RECORD_TIME_STATUS_PUNCH_WITH_LOCATION)
                                            ) {
                                                status.startResolutionForResult(
                                                    this@DashboardActivity,
                                                    hlth.care.view.dashboard.DashboardActivity.REQUEST_CHECK_SETTINGS_GPS
                                                )
                                            }*/
                                        }
                                    } catch (e: SendIntentException) {
                                        // Ignore the error.
                                    }
                                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("TAG", " Location Change $e")
        }
    }

    /**
     * Search location
     */

    //Timer for search member
    private var searchTimer: Timer? = null

    private fun dismissTimer() {
        if (searchTimer != null) {
            searchTimer!!.cancel()
        }
    }

    fun writeLocation(){
        /*binding.pickUpTextView.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Check - Text
                if (NetUtils.isNetworkAvailable(this@MapsActivity)) {

                    //Dismiss timer
                    dismissTimer()

                    //Start timer
                    searchTimer = Timer()
                    val timerTaskParticipates: TimerTask = object : TimerTask() {
                        override fun run() {
                            //TODO
                           runOnUiThread { //Call - API
                               searchLocation()
                            }
                        }
                    }
                    searchTimer!!.schedule(timerTaskParticipates, 1000)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })*/
    }
    fun searchLocation() {
        //val locationSearch:EditText = findViewById<EditText>(R.id.editText)
        lateinit var location: String
        location = binding.pickUpTextView.text.toString()
        var addressList: List<Address>? = null

        if (location == null || location == "") {
            Toast.makeText(applicationContext,"provide location",Toast.LENGTH_SHORT).show()
        }
        else{
            val geoCoder = Geocoder(this)
            try {
                addressList = geoCoder.getFromLocationName(location, 1)

            } catch (e: IOException) {
                e.printStackTrace()
            }

            Log.d("Tag"," Show user location : "+addressList)
            if(addressList != null) {
                if (addressList.size > 0) {
                    val address = addressList!![0]
                    Log.d("Tag"," Show user location : "+addressList.size)
                    val latLng = LatLng(address.latitude, address.longitude)
                    mMap!!.addMarker(MarkerOptions().position(latLng).title(location))
                    mMap!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                    Toast.makeText(
                        applicationContext,
                        address.latitude.toString() + " " + address.longitude,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    fun showCurrentAddressInPickUp(){
        val locationAddress = LocationAddress()

        if(currentLatLog != null) {
            locationAddress.getAddressFromLocation(
                currentLatLog!!.latitude,
                currentLatLog!!.longitude,
                applicationContext,
                GeoCodeHandler()
            )
        }
    }

    internal inner class GeoCodeHandler : Handler() {
        override fun handleMessage(message: Message) {
            var locationAddress: String = ""
            when(message.what){
                1 -> {
                    val bundle = message.data
                    locationAddress = bundle.getString("address").toString()
                }
            }

            binding.pickUpTextView.text = locationAddress
        }
    }
}

fun Context.mapIntent(): Intent {
    return Intent(this, MapsActivity::class.java)
}