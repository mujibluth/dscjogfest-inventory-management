package com.inventorylibrary.app.ui.inventory.multimedia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inventorylibrary.app.databinding.MultimediaFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MultimediaFragment : Fragment() {

    private val viewModel: MultimediaViewModel by viewModel()
    private var _binding: MultimediaFragmentBinding? = null
    private val binding: MultimediaFragmentBinding get() = _binding!!
    private lateinit var multimediaAdapter: MultimediaAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = MultimediaFragmentBinding.inflate(LayoutInflater.from(inflater.context), container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        multimediaAdapter = MultimediaAdapter()
        binding.multimediaRv.adapter = multimediaAdapter
        viewModel.multimediaList.observe(viewLifecycleOwner){
            multimediaAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}