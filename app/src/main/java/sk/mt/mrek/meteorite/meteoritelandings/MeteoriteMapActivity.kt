package sk.mt.mrek.meteorite.meteoritelandings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import sk.mt.mrek.meteorite.meteoritelandings.Constant.PICKED_METEORITE

class MeteoriteMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var meteorite: Model.MeteoriteLanding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meteorite_map)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        meteorite = intent.getSerializableExtra(PICKED_METEORITE) as Model.MeteoriteLanding
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
