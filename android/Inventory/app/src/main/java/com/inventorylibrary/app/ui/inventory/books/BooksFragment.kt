package com.inventorylibrary.app.ui.inventory.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inventorylibrary.app.databinding.BooksFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BooksFragment : Fragment() {

    private val viewModel: BooksViewModel by viewModel()
    private var _binding: BooksFragmentBinding? = null
    private val binding: BooksFragmentBinding get() = _binding!!
    private lateinit var bookAdapter: BookAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BooksFragmentBinding.inflate(LayoutInflater.from(inflater.context), container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bookAdapter = BookAdapter()
        binding.bookRv.adapter = bookAdapter
        viewModel.getBookList.observe(viewLifecycleOwner){ bookList ->
            bookAdapter.submitList(bookList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}