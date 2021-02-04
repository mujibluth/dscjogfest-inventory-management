package com.inventorylibrary.app.ui.borrow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inventorylibrary.app.dao.CatalogInOutDao
import com.inventorylibrary.app.entity.CatalogInOutEntity

class BorrowViewModel(private val catalogInOutDao: CatalogInOutDao) : ViewModel() {

    private var isShowOptionFab = false
    private val _showOptionFab = MutableLiveData<Boolean>()
    val showOptionFab: LiveData<Boolean> get() = _showOptionFab

    val catalogList: LiveData<List<CatalogInOutEntity>>
        get() = catalogInOutDao.getAllCatalog()

    fun setShowOptionFab(){
        isShowOptionFab = !isShowOptionFab
        _showOptionFab.value = isShowOptionFab
    }
}