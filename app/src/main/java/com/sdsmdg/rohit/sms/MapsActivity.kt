package com.sdsmdg.rohit.sms

import android.location.Geocoder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var lat = 0.0
    var lng = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        lat = intent.getDoubleExtra("lat", 0.0)
        lng = intent.getDoubleExtra("lng", 0.0)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val place = LatLng(lat, lng)

        mMap.addMarker(MarkerOptions().position(place).title("Marker is here"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(place))
        val geocoder = Geocoder(this@MapsActivity, Locale.getDefault())
        val address = geocoder.getFromLocation(lat, lng, 1).get(0).getAddressLine(0)
        Toast.makeText(this@MapsActivity, "" + address, Toast.LENGTH_LONG).show()
    }
}
