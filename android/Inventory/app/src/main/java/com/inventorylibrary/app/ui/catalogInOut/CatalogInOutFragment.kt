package com.inventorylibrary.app.ui.catalogInOut

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.inventorylibrary.app.R
import com.inventorylibrary.app.databinding.CatalogInOutFragmentBinding
import com.inventorylibrary.app.entity.BookEntity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class CatalogInOutFragment : Fragment() {

    private val viewModel: CatalogInOutViewModel by viewModel()
    private var _binding: CatalogInOutFragmentBinding? = null
    private val binding: CatalogInOutFragmentBinding get() = _binding!!
    private val args: CatalogInOutFragmentArgs by navArgs()
    private lateinit var catalogInOutAdapter: CatalogInOutAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = CatalogInOutFragmentBinding.inflate(LayoutInflater.from(inflater.context), container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val navBackStackEntry = findNavController().getBackStackEntry(R.id.catalogInOutFragment)

        val observer = LifecycleEventObserver{ _, event ->
            if (event == Lifecycle.Event.ON_RESUME && navBackStackEntry.savedStateHandle.contains("book_selected")){

                val bookSelected = navBackStackEntry.savedStateHandle.get<BookEntity>("book_selected")

                if (bookSelected != null){
                    viewModel.addSelectedBook(bookSelected)
                    navBackStackEntry.savedStateHandle.remove<BookEntity>("book_selected")
                }
            }
        }

        navBackStackEntry.lifecycle.addObserver(observer)
        viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY){
                navBackStackEntry.lifecycle.removeObserver(observer)
            }
        })

        catalogInOutAdapter = CatalogInOutAdapter()
        binding.catalogInOutRv.adapter = catalogInOutAdapter
        viewModel.selectedBook.observe(viewLifecycleOwner){ selectedBookList ->
            catalogInOutAdapter.submitList(selectedBookList)
        }

        lifecycleScope.launch(Dispatchers.IO){
            try {

                val member = viewModel.getMemberById(args.memberId) ?: return@launch
                withContext(Dispatchers.Main){
                    with(binding){
                        member.profilePicture?.let {
                            Picasso.get().load(Uri.parse(it)).into(memberImage)
                        }

                        memberName.text = member.name
                        memberID.text = member.memberID.toString()
                        memberAddress.text = member.address

                        addBookButton.setOnClickListener(
                            Navigation
                                .createNavigateOnClickListener(
                                    R.id.action_catalogInOutFragment_to_pickBookDialogFragment
                                )
                        )

                        saveButton.setOnClickListener {
                            lifecycleScope.launch(Dispatchers.IO){
                                try {
                                    viewModel.saveInOutTransaction(member.name, args.isCatalogOut)
                                    withContext(Dispatchers.Main){
                                        findNavController().navigateUp()
                                        Toast.makeText(
                                            requireContext(),
                                            "Berhasil ditambahkan",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }catch (e: Exception){}
                            }
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