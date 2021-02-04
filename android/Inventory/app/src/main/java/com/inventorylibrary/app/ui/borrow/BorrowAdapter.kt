package com.inventorylibrary.app.ui.borrow

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.inventorylibrary.app.databinding.ItemCatalogInOutBinding
import com.inventorylibrary.app.entity.CatalogInOutEntity
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class BorrowAdapter: ListAdapter<CatalogInOutEntity, BorrowAdapter.BorrowViewHolder>(
    object : DiffUtil.ItemCallback<CatalogInOutEntity>(){
        override fun areItemsTheSame(oldItem: CatalogInOutEntity, newItem: CatalogInOutEntity): Boolean {
            return oldItem.catalogID == newItem.catalogID
        }

        override fun areContentsTheSame(oldItem: CatalogInOutEntity, newItem: CatalogInOutEntity): Boolean {
            return oldItem == newItem
        }
    }

) {

    inner class BorrowViewHolder(private val binding: ItemCatalogInOutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(catalog: CatalogInOutEntity){
            with(binding){
                if (catalog.isCatalogIn) root.setBackgroundColor(Color.parseColor("#B9FFAD")) else root.setBackgroundColor(Color.parseColor("#FFD4D4"))

                catalogName.text = catalog.catalogName
                borrower.text = catalog.borrower
                isCatalogIn.text = if (catalog.isCatalogIn) "Masuk" else "Keluar"
                date.text = SimpleDateFormat("dd/MMMM/yyyy", Locale.getDefault()).format(Date(catalog.date))

                catalog.image?.let {
                    Picasso.get().load(it).into(bookCoverImage)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BorrowViewHolder {
        val binding = ItemCatalogInOutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BorrowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BorrowViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}