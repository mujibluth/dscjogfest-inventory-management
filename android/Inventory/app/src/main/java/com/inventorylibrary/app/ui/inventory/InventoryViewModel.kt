package com.inventorylibrary.app.ui.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InventoryViewModel : ViewModel() {

    private var isShowOptionFab = false
    private val _showOptionFab = MutableLiveData<Boolean>()
    val showOptionFab: LiveData<Boolean> get() = _showOptionFab

    fun setShowOptionFab(){
        isShowOptionFab = !isShowOptionFab
        _showOptionFab.value = isShowOptionFab
    }

}