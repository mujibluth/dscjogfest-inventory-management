package com.inventorylibrary.app.ui.inventory.multimedia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.inventorylibrary.app.MobileNavigationDirections
import com.inventorylibrary.app.databinding.ItemBookBinding
import com.inventorylibrary.app.entity.MultimediaEntity
import com.squareup.picasso.Picasso

class MultimediaAdapter: ListAdapter<MultimediaEntity, MultimediaAdapter.MultimediaViewHolder>(
    object : DiffUtil.ItemCallback<MultimediaEntity>(){
        override fun areItemsTheSame(oldItem: MultimediaEntity, newItem: MultimediaEntity): Boolean {
            return oldItem.multimediaID == newItem.multimediaID
        }

        override fun areContentsTheSame(oldItem: MultimediaEntity, newItem: MultimediaEntity): Boolean {
            return oldItem == newItem
        }
    }

) {

    inner class MultimediaViewHolder(private val binding: ItemBookBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(multimedia: MultimediaEntity){
            with(binding){
                bookTitle.text = multimedia.title
                type.text = "Multimedia"
                stock.text = multimedia.stock.toString()

                multimedia.image?.let {
                    Picasso.get().load(it).into(bookCoverImage)
                }
                root.setOnClickListener {
                    val direction = MobileNavigationDirections.actionGlobalMultimediaDetailFragment(multimedia.multimediaID!!)
                    it.findNavController().navigate(direction)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultimediaViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MultimediaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MultimediaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}