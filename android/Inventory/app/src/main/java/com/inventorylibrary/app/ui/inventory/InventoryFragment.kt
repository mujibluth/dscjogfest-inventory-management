package com.inventorylibrary.app.ui.inventory

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.inventorylibrary.app.R
import com.inventorylibrary.app.databinding.FragmentInventoryBinding

class InventoryFragment : Fragment() {

    private lateinit var viewModel: InventoryViewModel
    private var isFabOpen: Boolean = false
    private var _binding: FragmentInventoryBinding? = null
    private val binding: FragmentInventoryBinding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInventoryBinding.inflate(LayoutInflater.from(inflater.context), container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(InventoryViewModel::class.java)

        binding.inventoryViewPager.adapter = InventoryPagerAdapter(this)
        val tabsTitle = listOf("Buku", "Multimedia")
        TabLayoutMediator(binding.tabLayout, binding.inventoryViewPager){tab,position ->
            tab.text = tabsTitle[position]
        }.attach()

        with(binding){
            addFAB.setOnClickListener {
                viewModel.setShowOptionFab()
            }
            fabClickedBg.setOnClickListener {
                viewModel.setShowOptionFab()
            }
            addBookButton.setOnClickListener {
                viewModel.setShowOptionFab()
                findNavController().navigate(R.id.action_nav_inventory_to_addBookFragment)
            }
            addMultimediaButton.setOnClickListener {
                viewModel.setShowOptionFab()
                findNavController().navigate(R.id.action_nav_inventory_to_addMultimediaFragment)
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
            addFAB.isSelected = true
            binding.fabClickedBg.visibility = View.VISIBLE
            addMultimediaButton.visibility = View.VISIBLE
            addBookButton.visibility = View.VISIBLE
            addBookButton.animate().translationY(-250f)
            addMultimediaButton.animate().translationY(-150F)
        }
    }

    private fun hideOptionsFab() {
        with(binding){
            addFAB.isSelected = false
            addMultimediaButton.animate().translationY(0f)
            addBookButton.animate().translationY(0f).setListener(object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    if (!isFabOpen){
                        addMultimediaButton.visibility = View.GONE
                        addBookButton.visibility = View.GONE
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