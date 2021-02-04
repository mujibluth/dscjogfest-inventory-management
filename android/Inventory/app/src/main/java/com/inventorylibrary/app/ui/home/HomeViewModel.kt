package com.inventorylibrary.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inventorylibrary.app.dao.BookDao
import com.inventorylibrary.app.dao.MemberDao

class HomeViewModel(
    private val bookDao: BookDao,
    private val memberDao: MemberDao
) : ViewModel() {

    val bookSize: LiveData<List<Int>>
        get() = bookDao.getBookSize()

    val memberSize: LiveData<List<Int>>
        get() = memberDao.getMemberSize()
}