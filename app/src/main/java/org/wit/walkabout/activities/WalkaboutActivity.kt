package org.wit.walkabout.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.walkabout.R
import org.wit.walkabout.databinding.ActivityWalkaboutBinding
import org.wit.walkabout.helpers.showImagePicker
import org.wit.walkabout.main.MainApp
import org.wit.walkabout.models.Location
import org.wit.walkabout.models.WalkaboutModel
import timber.log.Timber.i
class WalkaboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWalkaboutBinding
    var walk = WalkaboutModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>
    var edit = false
    private lateinit var radioGroupDifficulty: RadioGroup
    private lateinit var radioGroupTerrain: RadioGroup


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        edit = false

        binding = ActivityWalkaboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        i("Walkabout Activity started")

        radioGroupDifficulty = findViewById(R.id.radioGroupDifficulty)
        radioGroupTerrain = findViewById(R.id.radioGroupTerrain)

        radioGroupDifficulty.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1) {
                val selectedRadioButton = findViewById<RadioButton>(checkedId)
                val selectedDifficulty = selectedRadioButton.text.toString()
                walk.difficulty = selectedDifficulty
                binding.difficulty.setText(selectedDifficulty)
            }
        }

        radioGroupTerrain.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1) {
                val selectedRadioButton = findViewById<RadioButton>(checkedId)
                val selectedTerrain = selectedRadioButton.text.toString()
                walk.terrain = selectedTerrain
                binding.terrain.setText(selectedTerrain)
            }
        }

        /*        NumberPicker numberPicker = findViewById(R.id.numberPicker);
        if (numberPicker != null) {
            numberPicker.setMinValue(0);
            numberPicker.setMaxValue(10);
            numberPicker.setWrapSelectorWheel(true);
            numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    String text = "Changed from " + oldVal + " to " + newVal;
                    Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }*/


        if (intent.hasExtra("walk_edit")) {
            edit = true
            walk = intent.extras?.getParcelable("walk_edit")!!
            binding.walkTitle.setText(walk.title)
            binding.description.setText(walk.description)
            binding.difficulty.setText(walk.difficulty)
            binding.terrain.setText(walk.terrain)
            binding.btnAdd.setText(R.string.save_walk)
            Picasso.get()
                .load(walk.image)
                .into(binding.walkImage)
            if (walk.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_walk_image)
            }
        }

        binding.btnAdd.setOnClickListener() {
            walk.title = binding.walkTitle.text.toString()
            walk.description = binding.description.text.toString()
            walk.difficulty = binding.difficulty.text.toString()
            walk.terrain = binding.terrain.text.toString()
            if (walk.title.isEmpty()) {
                Snackbar.make(it, R.string.enter_walk_title, Snackbar.LENGTH_LONG).show()
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
            showImagePicker(imageIntentLauncher, this)
            i("Select image")
        }

        binding.walkLocation.setOnClickListener {
            val location = Location(52.22, -6.93, 15f)
            if (walk.zoom != 0f) {
                location.lat = walk.lat
                location.lng = walk.lng
                location.zoom = walk.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        registerImagePickerCallback()
        registerMapCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_walkabout, menu)
        if (edit) menu.getItem(0).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                setResult(99)
                app.walks.delete(walk)
                finish()
            }

            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")

                            val image = result.data!!.data!!
                            contentResolver.takePersistableUriPermission(
                                image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION
                            )
                            walk.image = image

                            Picasso.get()
                                .load(walk.image)
                                .into(binding.walkImage)
                            binding.chooseImage.setText(R.string.change_walk_image)
                        } // end of if
                    }

                    RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location =
                                result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            walk.lat = location.lat
                            walk.lng = location.lng
                            walk.zoom = location.zoom
                        } // end of if
                    }

                    RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }
}

//    public void addListenerOnButton() {
//
//        radioGroup = (RadioGroup) findViewById(R.id.radio)
//        btnDisplay = (Button) findViewById(R.id.btnDisplay)
//
//        btnDisplay.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                // get selected radio button from radioGroup
//                int selectedId = radioGroup.getCheckedRadioButtonId()
//
//                // find the radiobutton by returned id
//                radioButton = (RadioButton) findViewById(selectedId)
//
//}