package com.inventorylibrary.app.ui.pickBook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.inventorylibrary.app.MobileNavigationDirections
import com.inventorylibrary.app.databinding.ItemBookBinding
import com.inventorylibrary.app.databinding.PickBookFragmentBinding
import com.inventorylibrary.app.entity.BookEntity
import com.inventorylibrary.app.ui.catalogInOut.CatalogInOutViewModel
import com.inventorylibrary.app.ui.inventory.books.BookAdapter
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.getViewModel

class PickBookDialogFragment : BottomSheetDialogFragment() {

    private val binding: PickBookFragmentBinding get() = _binding!!
    private var _binding: PickBookFragmentBinding? = null
    private lateinit var pickBookAdapter: PickBookAdapter
    private val viewModel by lazy {
        requireParentFragment().getViewModel<CatalogInOutViewModel>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PickBookFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        pickBookAdapter = PickBookAdapter()
        binding.bookRv.adapter = pickBookAdapter
        viewModel.getBookList.observe(viewLifecycleOwner){ bookList ->
            pickBookAdapter.submitList(bookList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class PickBookAdapter: ListAdapter<BookEntity, PickBookViewHolder>(
        object : DiffUtil.ItemCallback<BookEntity>(){
            override fun areItemsTheSame(oldItem: BookEntity, newItem: BookEntity): Boolean {
                return oldItem.bookID == newItem.bookID
            }

            override fun areContentsTheSame(oldItem: BookEntity, newItem: BookEntity): Boolean {
                return oldItem == newItem
            }
        }

    ) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickBookViewHolder {
            val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PickBookViewHolder(binding)
        }

        override fun onBindViewHolder(holder: PickBookViewHolder, position: Int) {
            holder.bind(getItem(position))
        }
    }

    inner class PickBookViewHolder(private val binding: ItemBookBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(book: BookEntity){
            with(binding){
                bookTitle.text = book.title
                type.text = "Buku"
                stock.text = book.stock.toString()

                book.coverImagePath?.let {
                    Picasso.get().load(it).into(bookCoverImage)
                }
                root.setOnClickListener {
                    findNavController().apply {
                        previousBackStackEntry?.savedStateHandle?.set("book_selected", book)
                        navigateUp()
                    }
                }
            }
        }
    }
}