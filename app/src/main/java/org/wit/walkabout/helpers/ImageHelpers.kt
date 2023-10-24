package org.wit.walkabout.helpers

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import org.wit.walkabout.R

fun showImagePicker(intentLauncher : ActivityResultLauncher<Intent>) {
    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
    chooseFile.type = "image/*"
    chooseFile = Intent.createChooser(chooseFile, R.string.select_walk_image.toString())
    intentLauncher.launch(chooseFile)
}