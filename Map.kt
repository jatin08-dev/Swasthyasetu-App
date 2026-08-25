package com.example.animation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.LocationServices
import org.json.JSONArray
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.net.HttpURLConnection
import java.net.URL

class Map : AppCompatActivity() {
    lateinit var get: Button
    private lateinit var map: MapView
    private var userLat = 0.0
    private var userLon = 0.0

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_map)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Configuration.getInstance().load(
            applicationContext,
            androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext)
        )

        map = findViewById(R.id.map)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
get=findViewById(R.id.get)
        get.setOnClickListener { searchNearby("hospital") }
        checkPermissionsAndGetLocation()

    }

    private fun checkPermissionsAndGetLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            getLocation()
        }
    }

    private fun getLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Permission check again for compiler safety
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    userLat = it.latitude
                    userLon = it.longitude
                    val userPoint = GeoPoint(userLat, userLon)

                    map.controller.setZoom(15.0)
                    map.controller.setCenter(userPoint)

                    updateUserMarker(userPoint)
                }
            }
        }
    }

    private fun updateUserMarker(point: GeoPoint) {
        // Clear previous user markers if any
        val userMarker = Marker(map)
        userMarker.position = point
        userMarker.title = "You are here"
        userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        map.overlays.add(userMarker)
        map.invalidate()
    }

    private fun searchNearby(query: String) {
        // Include lat/lon and a small viewbox (around 0.1 degrees) to bias results to nearby
        val urlString = "https://nominatim.openstreetmap.org/search?" +
                "format=json&q=$query&limit=15&lat=$userLat&lon=$userLon&bounded=1" +
                "&viewbox=${userLon-0.1},${userLat+0.1},${userLon+0.1},${userLat-0.1}"

        Thread {
            try {
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.setRequestProperty("User-Agent", "RajputProjectApp") // Required by Nominatim

                val response = connection.inputStream.bufferedReader().use { it.readText() }
                val jsonArray = JSONArray(response)

                runOnUiThread {
                    map.overlays.clear()
                    updateUserMarker(GeoPoint(userLat, userLon)) // Keep user visible

                    for (i in 0 until jsonArray.length()) {
                        val obj = jsonArray.getJSONObject(i)
                        val lat = obj.getDouble("lat")
                        val lon = obj.getDouble("lon")
                        val name = obj.getString("display_name").split(",")[0] // Use shorter name

                        val marker = Marker(map)
                        marker.position = GeoPoint(lat, lon)
                        marker.title = name
                        map.overlays.add(marker)
                    }
                    map.invalidate()
                    if (jsonArray.length() == 0) {
                        Toast.makeText(this, "No $query found nearby", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread { Toast.makeText(this, "Search failed", Toast.LENGTH_SHORT).show() }
            }
        }.start()
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLocation()
        }
    }
}
