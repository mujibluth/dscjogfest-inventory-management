package com.inventorylibrary.app.ui.multimediaDetail

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.inventorylibrary.app.databinding.MultimediaDetailFragmentBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class MultimediaDetailFragment : Fragment() {

    private val viewModel: MultimediaDetailViewModel by viewModel()
    private val args: MultimediaDetailFragmentArgs by navArgs()
    private var _binding: MultimediaDetailFragmentBinding? = null
    private val binding: MultimediaDetailFragmentBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = MultimediaDetailFragmentBinding.inflate(LayoutInflater.from(inflater.context), container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO){
            try {
                val multimedia = viewModel.getMultimediaById(args.multimediaID) ?: return@launch
                withContext(Dispatchers.Main){
                    with(binding){
                        multimediaTitle.text = multimedia.title
                        publisher.text = multimedia.publisher
                        yearPublish.text = multimedia.yearPublish
                        type.text = "Multimedia"
                        multimediaStock.text = multimedia.stock.toString()
                        multimediaRack.text = multimedia.rack
                        description.text = multimedia.description
                        multimedia.image?.let {
                            Picasso.get().load(Uri.parse(multimedia.image)).into(multimediaImage)
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