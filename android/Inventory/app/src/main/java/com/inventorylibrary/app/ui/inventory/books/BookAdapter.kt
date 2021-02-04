package com.inventorylibrary.app.ui.inventory.books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.inventorylibrary.app.MobileNavigationDirections
import com.inventorylibrary.app.databinding.ItemBookBinding
import com.inventorylibrary.app.entity.BookEntity
import com.squareup.picasso.Picasso

class BookAdapter: ListAdapter<BookEntity, BookAdapter.BookViewHolder>(
    object : DiffUtil.ItemCallback<BookEntity>(){
        override fun areItemsTheSame(oldItem: BookEntity, newItem: BookEntity): Boolean {
            return oldItem.bookID == newItem.bookID
        }

        override fun areContentsTheSame(oldItem: BookEntity, newItem: BookEntity): Boolean {
            return oldItem == newItem
        }
    }

) {

    inner class BookViewHolder(private val binding: ItemBookBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(book: BookEntity){
            with(binding){
                bookTitle.text = book.title
                type.text = "Buku"
                stock.text = book.stock.toString()

                book.coverImagePath?.let {
                    Picasso.get().load(it).into(bookCoverImage)
                }
                root.setOnClickListener {
                    val direction = MobileNavigationDirections.actionGlobalBookDetailFragment(book.bookID!!)
                    it.findNavController().navigate(direction)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}