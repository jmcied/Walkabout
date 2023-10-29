package org.wit.walkabout.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import org.wit.walkabout.databinding.ActivityWalkaboutMapsBinding
import org.wit.walkabout.databinding.ContentWalkaboutMapsBinding

class WalkaboutMapsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWalkaboutMapsBinding
    private lateinit var contentBinding: ContentWalkaboutMapsBinding
    lateinit var map: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWalkaboutMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        contentBinding = ContentWalkaboutMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)

        }
    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }
}
