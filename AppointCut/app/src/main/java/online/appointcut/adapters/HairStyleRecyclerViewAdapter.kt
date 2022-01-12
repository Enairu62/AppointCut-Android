package online.appointcut.adapters

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import coil.load
import online.appointcut.databinding.ItemHairstyleBinding

import online.appointcut.models.HairStyle
import online.appointcut.network.BASE_URL

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class HairStyleRecyclerViewAdapter(
    private val values: List<HairStyle>
) : RecyclerView.Adapter<HairStyleRecyclerViewAdapter.ViewHolder>() {

    private var onItemClick: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHairstyleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.styleName.text = item.name
        holder.styleDescription.text = item.description
        Log.d("HSAdapter", "$BASE_URL/${item.imageLink}")
        holder.styleImage.load("$BASE_URL/${item.imageLink}")
        holder.hairstyle = item
        onItemClick?.let {
            holder.setOnclickListener(it)
        }
    }

    override fun getItemCount(): Int = values.size

    fun setItemClickListener(listener: ItemClickListener){
        onItemClick = listener
    }

     fun interface ItemClickListener{
         fun onItemClick(hairstyle: HairStyle)
    }

    inner class ViewHolder(private val binding: ItemHairstyleBinding) : RecyclerView.ViewHolder(binding.root) {
        val styleName = binding.styleName
        val styleDescription  = binding.styleDescription
        val styleImage = binding.styleImage
        lateinit var hairstyle: HairStyle


        override fun toString(): String {
            return super.toString() + " '" + styleName.text + "'"
        }
        fun setOnclickListener(listener: ItemClickListener){
            binding.root.setOnClickListener {
                listener.onItemClick(hairstyle)
            }
        }
    }

}