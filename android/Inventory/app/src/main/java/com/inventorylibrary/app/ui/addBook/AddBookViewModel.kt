package com.inventorylibrary.app.ui.addBook

import androidx.lifecycle.ViewModel
import com.inventorylibrary.app.dao.BookDao
import com.inventorylibrary.app.entity.BookEntity

class AddBookViewModel(private val bookDao: BookDao) : ViewModel() {

    suspend fun addBook(book: BookEntity) = bookDao.addBook(book)
}