package com.inventorylibrary.app.ui.borrow

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.inventorylibrary.app.MobileNavigationDirections
import com.inventorylibrary.app.databinding.FragmentBorrowBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BorrowFragment : Fragment() {

    private val viewModel: BorrowViewModel by viewModel()
    private var isFabOpen: Boolean = false
    private var _binding: FragmentBorrowBinding? = null
    private val binding: FragmentBorrowBinding get() = _binding!!
    private lateinit var borrowAdapter: BorrowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBorrowBinding.inflate(LayoutInflater.from(inflater.context), container, false)
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        borrowAdapter = BorrowAdapter()

        with(binding){
            borrowRv.adapter = borrowAdapter

            viewModel.catalogList.observe(viewLifecycleOwner){ catalogList ->
                borrowAdapter.submitList(catalogList)
            }

            borowFAB.setOnClickListener {
                viewModel.setShowOptionFab()
            }
            fabClickedBg.setOnClickListener {
                viewModel.setShowOptionFab()
            }
            borrowButton.setOnClickListener {
                val direction = MobileNavigationDirections.actionGlobalPickMemberDialogFragment(true)
                findNavController().navigate(direction)
                viewModel.setShowOptionFab()
            }
            returnButton.setOnClickListener {
                val direction = MobileNavigationDirections.actionGlobalPickMemberDialogFragment(false)
                findNavController().navigate(direction)
                viewModel.setShowOptionFab()
            }
        }

        viewModel.showOptionFab.observe(viewLifecycleOwner){ isShow ->
            isFabOpen = isShow
            if (isShow){
                showOptionsFab()
            }else{
                hideOptionsFab()
            }
        }

    }

    private fun showOptionsFab() {
        with(binding){
            borowFAB.isSelected = true
            binding.fabClickedBg.visibility = View.VISIBLE
            borrowButton.visibility = View.VISIBLE
            returnButton.visibility = View.VISIBLE
            borrowButton.animate().translationY(-250f)
            returnButton.animate().translationY(-150F)
        }
    }

    private fun hideOptionsFab() {
        with(binding){
            borowFAB.isSelected = false
            returnButton.animate().translationY(0f)
            borrowButton.animate().translationY(0f).setListener(object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    if (!isFabOpen){
                        returnButton.visibility = View.GONE
                        borrowButton.visibility = View.GONE
                    }
                }
            })
            binding.fabClickedBg.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}