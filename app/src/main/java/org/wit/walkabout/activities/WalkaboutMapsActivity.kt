package org.wit.walkabout.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.wit.walkabout.databinding.ActivityWalkaboutMapsBinding

class WalkaboutMapsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWalkaboutMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWalkaboutMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        }
    }
