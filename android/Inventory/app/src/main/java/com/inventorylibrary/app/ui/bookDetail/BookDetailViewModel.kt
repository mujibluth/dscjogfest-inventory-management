package com.inventorylibrary.app.ui.bookDetail

import androidx.lifecycle.ViewModel
import com.inventorylibrary.app.dao.BookDao

class BookDetailViewModel(private val bookDao: BookDao) : ViewModel() {

    suspend fun getBookById(bookID: Int) = bookDao.getBookById(bookID)
}