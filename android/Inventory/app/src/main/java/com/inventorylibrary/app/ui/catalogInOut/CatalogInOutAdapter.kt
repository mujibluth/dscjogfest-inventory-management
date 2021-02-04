package com.inventorylibrary.app.ui.catalogInOut

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.inventorylibrary.app.databinding.ItemBookBinding
import com.inventorylibrary.app.entity.BookEntity
import com.squareup.picasso.Picasso

class CatalogInOutAdapter: ListAdapter<BookEntity, CatalogInOutAdapter.CatalogInOutViewHolder>(
    object : DiffUtil.ItemCallback<BookEntity>(){
        override fun areItemsTheSame(oldItem: BookEntity, newItem: BookEntity): Boolean {
            return oldItem.bookID == newItem.bookID
        }

        override fun areContentsTheSame(oldItem: BookEntity, newItem: BookEntity): Boolean {
            return oldItem == newItem
        }
    }

) {

    inner class CatalogInOutViewHolder(private val binding: ItemBookBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(book: BookEntity){
            with(binding){
                bookTitle.text = book.title
                type.text = "Buku"
                stock.text = book.stock.toString()

                book.coverImagePath?.let {
                    Picasso.get().load(it).into(bookCoverImage)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogInOutViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatalogInOutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatalogInOutViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}