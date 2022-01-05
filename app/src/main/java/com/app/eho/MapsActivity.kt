package com.app.eho

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.app.eho.databinding.ActivityMapsBinding
import androidx.lifecycle.Transformations.map
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback
import androidx.lifecycle.Transformations.map
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener
import com.google.android.gms.maps.model.*
import android.graphics.Bitmap

import android.R

import android.graphics.drawable.BitmapDrawable





class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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

        //var markersOption: MutableIterable<MarkerOptions> = MutableLiveData<MarkerOptions>()

        // Add a marker in Sydney and move the camera
        var latLng1 = LatLng(-34.0, 151.0)
        var latLng2 = LatLng(28.439147000000002, 77.0944446)
        var latLng3 = LatLng(28.433147, 77.0952446)
        var latLng4 = LatLng(28.440547000000002, 77.1026446)

        var m1 : MarkerOptions = MarkerOptions().position(latLng1).title("Marker in Sydney").snippet("Population: 4,627,300").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car))
        var m2 : MarkerOptions = MarkerOptions().position(latLng2).title("Marker in Group1").snippet("Population: 4,627").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car))
        var m3 : MarkerOptions = MarkerOptions().position(latLng3).title("Marker in Group2").snippet("Population: 4,6").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car))
        var m4 : MarkerOptions = MarkerOptions().position(latLng4).title("Marker in Group3").snippet("Population: 4,627,3").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car))

       // mMap.addMarker(m1)
        mMap.addMarker(m2)
        mMap.addMarker(m3)
        mMap.addMarker(m4)

       /* // Add a marker in Sydney and move the camera
        sydney = LatLng(28.440547000000002, 77.1026446)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in sydney 2"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/

        val builder = LatLngBounds.builder()
        //builder.include(latLng1)
        builder.include(latLng2)
        builder.include(latLng3)
        builder.include(latLng4)

        //create a bound
        val bounds = builder.build()

        //set a 30 pixels padding from the edge of the screen
        mMap.setOnMapLoadedCallback(OnMapLoadedCallback {
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

    }


}