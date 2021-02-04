package com.inventorylibrary.app.ui.bookDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.inventorylibrary.app.databinding.BookDetailFragmentBinding
import com.inventorylibrary.app.entity.BookEntity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookDetailFragment : Fragment() {

    private val viewModel: BookDetailViewModel by viewModel()
    private val args: BookDetailFragmentArgs by navArgs()
    private var _binding: BookDetailFragmentBinding? = null
    private val binding: BookDetailFragmentBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BookDetailFragmentBinding.inflate(LayoutInflater.from(inflater.context), container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO){
            try {

                val book: BookEntity = viewModel.getBookById(args.bookID) ?: return@launch
                withContext(Dispatchers.Main){
                    with(binding){
                        bookTitle.text = book.title
                        bookWriter.text = book.writer
                        yearPublish.text = book.year.toString()
                        bookGenre.text = book.genre
                        bookIsbn.text = book.isbn
                        bookDescription.text = book.notes
                        bookRack.text = book.rack
                        bookStock.text = book.stock.toString()
                        book.coverImagePath?.let {
                            Picasso.get().load(it).into(bookCoverImage)
                        }
                    }
                }
            }catch (e: Exception){}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}