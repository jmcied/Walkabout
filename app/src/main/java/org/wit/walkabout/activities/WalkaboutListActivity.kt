package org.wit.walkabout.activities

import WalkaboutAdapter
import WalkaboutListener
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.walkabout.R
import org.wit.walkabout.databinding.ActivityWalkaboutListBinding
import org.wit.walkabout.main.MainApp
import org.wit.walkabout.models.WalkaboutModel

class WalkaboutListActivity : AppCompatActivity(), WalkaboutListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityWalkaboutListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalkaboutListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = WalkaboutAdapter(app.walks.findAll(),this)
    }

        override fun onCreateOptionsMenu(menu: Menu): Boolean {
            menuInflater.inflate(R.menu.menu_main, menu)
            return super.onCreateOptionsMenu(menu)
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, WalkaboutActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.walks.findAll().size)
            }
        }
    override fun onWalkaboutClick(walk: WalkaboutModel) {
        val launcherIntent = Intent(this, WalkaboutActivity::class.java)
        launcherIntent.putExtra("walk_edit", walk)
        getClickResult.launch(launcherIntent)
    }

    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.walks.findAll().size)
            }
        }
    }

