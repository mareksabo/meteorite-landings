package sk.mt.mrek.meteorite.meteoritelandings.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import sk.mt.mrek.meteorite.meteoritelandings.adapters.MultilineInfoWindowAdapter
import sk.mt.mrek.meteorite.meteoritelandings.R
import sk.mt.mrek.meteorite.meteoritelandings.models.Meteorite
import sk.mt.mrek.meteorite.meteoritelandings.utils.Constant.PICKED_METEORITE
import sk.mt.mrek.meteorite.meteoritelandings.utils.toSimpleString

class MeteoriteMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var meteorite: Meteorite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meteorite_map)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val stringExtra = intent.getStringExtra(PICKED_METEORITE)
        meteorite = Gson().fromJson<Meteorite>(stringExtra, Meteorite::class.java)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setInfoWindowAdapter(MultilineInfoWindowAdapter(applicationContext))

        val coordinate = LatLng(meteorite.reclat, meteorite.reclong)
        map.addMarker(createMarkerOptions(coordinate))
                .showInfoWindow()
        map.moveCamera(CameraUpdateFactory.newLatLng(coordinate))
    }

    private fun createMarkerOptions(coordinate: LatLng) = MarkerOptions()
            .position(coordinate)
            .title(createTitle())
            .snippet(createSnippet())

    private fun createTitle() = meteorite.name

    private fun createSnippet() =
            "coordinates: ${meteorite.reclat}°, ${meteorite.reclong}° " + newLine() +
                    "nametype: ${meteorite.nametype} " + newLine() +
                    "recclass: ${meteorite.recclass} " + newLine() +
                    "mass (g): ${meteorite.mass}" + newLine() +
                    "fall: ${meteorite.fall}" + newLine() +
                    "date: ${meteorite.year.toSimpleString()}"


    private fun newLine() = System.getProperty("line.separator")
}
