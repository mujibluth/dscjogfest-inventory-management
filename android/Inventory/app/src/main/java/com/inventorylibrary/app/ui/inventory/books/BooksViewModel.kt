package com.inventorylibrary.app.ui.inventory.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inventorylibrary.app.dao.BookDao
import com.inventorylibrary.app.entity.BookEntity

class BooksViewModel(private val bookDao: BookDao) : ViewModel() {

    val getBookList: LiveData<List<BookEntity>>
        get() = bookDao.getBookList()
}