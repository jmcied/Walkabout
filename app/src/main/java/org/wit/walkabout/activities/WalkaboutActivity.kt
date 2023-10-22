package org.wit.walkabout.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.wit.walkabout.databinding.ActivityWalkaboutBinding
import org.wit.walkabout.main.MainApp
import org.wit.walkabout.models.WalkaboutModel
import timber.log.Timber.i

class WalkaboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWalkaboutBinding
    var walk = WalkaboutModel()
    val walks = ArrayList<WalkaboutModel>()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalkaboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Walkabout Activity started")

        binding.btnAdd.setOnClickListener() {
            walk.title = binding.walkTitle.text.toString()
            walk.description = binding.description.text.toString()
            walk.difficulty = binding.difficulty.text.toString()
            walk.terrain = binding.terrain.text.toString()
            if (walk.title.isNotEmpty()) {
                app.walks.add(walk.copy())
                i("add Button Pressed: ${walk}")
                for (i in app.walks.indices) {
                    i("Walk[$i]:${this.app.walks[i]}")
                }
                setResult(RESULT_OK)
                finish()
            } else {
                Snackbar
                    .make(it, "Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}