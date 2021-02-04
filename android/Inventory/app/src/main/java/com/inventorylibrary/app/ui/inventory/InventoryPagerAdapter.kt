package com.inventorylibrary.app.ui.inventory

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.inventorylibrary.app.ui.inventory.books.BooksFragment
import com.inventorylibrary.app.ui.inventory.multimedia.MultimediaFragment

class InventoryPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    private val fragmentList = listOf(
            BooksFragment(),
            MultimediaFragment()
    )

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}