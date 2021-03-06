package com.pedro.mapsandroid

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.pedro.mapsandroid.utils.PermissionUtils

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var locationListenet: LocationListener

    fun initLocationListener(){
        this.locationListenet = object : LocationListener{
            override fun onLocationChanged(location: Location?) {

                val currentLocation = LatLng(location?.latitude!!, location?.longitude!!)
                mMap.addMarker(MarkerOptions().position(currentLocation))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f))
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onProviderEnabled(provider: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onProviderDisabled(provider: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        PermissionUtils.validatePermissions(
                listOf(Manifest.permission.ACCESS_FINE_LOCATION),
                this,
                1
        )

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        for (permission in grantResults) {

            if (permission == PackageManager.PERMISSION_DENIED){
                Toast.makeText(this,
                        "PermissionDenied",
                        Toast.LENGTH_SHORT).show()
            } else {
                requestLocationUpdates()

            }
        }

    }

    @SuppressLint("MissingPermission") //Force unrap of permission, already checked
    private fun requestLocationUpdates() {
        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            0, 0f,
                            locationListenet)
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

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        this.initLocationListener()
        //this.requestLocationUpdates()
        //Click
        mMap.setOnMapClickListener {
            mMap.addMarker(MarkerOptions()
                    .position(it))
        }

        //Hold
        mMap.setOnMapLongClickListener {
            mMap.addMarker(MarkerOptions()
                    .position(it))
        }

        // Add a marker in Sydney and move the camera
        val FIAP = LatLng(-23.5565804, -46.662113)

        mMap.addMarker(MarkerOptions()
                .position(FIAP)
                .title("FIAP Paulista")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(FIAP, 15f))

        this.addCircularForm(FIAP)
    }

    private fun addCircularForm(localCoords: LatLng) {

        val circle = CircleOptions()
        circle.center(localCoords)
        circle.radius(200.0)
        circle.fillColor(Color.argb(128,10,10,128))
        circle.strokeColor(Color.argb(128,128,0,0))

        mMap.addCircle(circle)
    }
}
