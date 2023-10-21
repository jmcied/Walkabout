package org.wit.walkabout.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.wit.walkabout.R
import org.wit.walkabout.main.MainApp

class WalkaboutListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_walkabout_list)
        app = application as MainApp
    }
}