package org.wit.walkabout.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.walkabout.R
import org.wit.walkabout.databinding.ActivityWalkaboutBinding
import org.wit.walkabout.helpers.showImagePicker
import org.wit.walkabout.main.MainApp
import org.wit.walkabout.models.WalkaboutModel
import timber.log.Timber.i

class WalkaboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWalkaboutBinding
    var walk = WalkaboutModel()
    val walks = ArrayList<WalkaboutModel>()
    lateinit var app: MainApp
    var edit = false
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalkaboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        i("Walkabout Activity started")

        if (intent.hasExtra("walk_edit")) {
            edit = true
            walk = intent.extras?.getParcelable("walk_edit")!!
            binding.walkTitle.setText(walk.title)
            binding.description.setText(walk.description)
            binding.difficulty.setText(walk.difficulty)
            binding.terrain.setText(walk.terrain)
            binding.btnAdd.setText(R.string.save_walk)
        }

        binding.btnAdd.setOnClickListener() {
            walk.title = binding.walkTitle.text.toString()
            walk.description = binding.description.text.toString()
            walk.difficulty = binding.difficulty.text.toString()
            walk.terrain = binding.terrain.text.toString()
            if (walk.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_walk_title, Snackbar.LENGTH_LONG).show()
            } else {
                if (edit) {
                    app.walks.update(walk.copy())
                } else {
                    app.walks.create(walk.copy())
                }
            }
                setResult(RESULT_OK)
                finish()
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
            i("Select image")
        }

        registerImagePickerCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_walkabout, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            walk.image = result.data!!.data!!
                            Picasso.get()
                                .load(walk.image)
                                .into(binding.walkImage)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

}