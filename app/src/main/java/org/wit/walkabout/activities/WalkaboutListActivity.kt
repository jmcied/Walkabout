package org.wit.walkabout.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wit.walkabout.R
import org.wit.walkabout.databinding.ActivityWalkaboutListBinding
import org.wit.walkabout.databinding.CardWalkaboutBinding
import org.wit.walkabout.main.MainApp
import org.wit.walkabout.models.WalkaboutModel

class WalkaboutListActivity : AppCompatActivity() {

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
        binding.recyclerView.adapter = WalkaboutAdapter(app.walks)
    }

        override fun onCreateOptionsMenu(menu: Menu): Boolean {
            menuInflater.inflate(R.menu.menu_main, menu)
            return super.onCreateOptionsMenu(menu)
        }
    }




class WalkaboutAdapter constructor(private var walks: List<WalkaboutModel>) :
    RecyclerView.Adapter<WalkaboutAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardWalkaboutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val walk = walks[holder.adapterPosition]
        holder.bind(walk)
    }

    override fun getItemCount(): Int = walks.size

    class MainHolder(private val binding : CardWalkaboutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(walk: WalkaboutModel) {
            binding.walkTitle.text = walk.title
            binding.description.text = walk.description
            binding.difficulty.text = walk.difficulty
            binding.terrain.text = walk.terrain
        }
    }
}