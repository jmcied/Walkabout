import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.walkabout.databinding.CardWalkaboutBinding
import org.wit.walkabout.models.WalkaboutModel

interface WalkaboutListener {
    fun onWalkaboutClick(placemark: WalkaboutModel, position: Int)
}
class WalkaboutAdapter constructor(private var walks: List<WalkaboutModel>,
                                    private val listener: WalkaboutListener) :
    RecyclerView.Adapter<WalkaboutAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardWalkaboutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val walk = walks[holder.adapterPosition]
        holder.bind(walk, listener)
    }

    override fun getItemCount(): Int = walks.size

    class MainHolder(private val binding : CardWalkaboutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(walk: WalkaboutModel, listener: WalkaboutListener) {
            binding.walkTitle.text = walk.title
            binding.description.text = walk.description
            binding.difficulty.text = walk.difficulty
            binding.terrain.text = walk.terrain
            Picasso.get().load(walk.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onWalkaboutClick(walk, adapterPosition) }
        }
    }
}