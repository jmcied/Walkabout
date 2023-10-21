package org.wit.walkabout.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.wit.walkabout.databinding.ActivityWalkaboutBinding
import org.wit.walkabout.models.WalkaboutModel
import timber.log.Timber
import timber.log.Timber.i

class WalkaboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWalkaboutBinding
    var walk = WalkaboutModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWalkaboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())
        i("Placemark Activity started...")

        binding.btnAdd.setOnClickListener() {
            walk.title = binding.walkTitle.text.toString()
            if (walk.title.isNotEmpty()) {
                i("add Button Pressed: ${walk.title}")
            } else {
                Snackbar
                    .make(it, "Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}