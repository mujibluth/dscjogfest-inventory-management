package com.inventorylibrary.app.ui.catalogInOut

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.withTransaction
import com.inventorylibrary.app.dao.BookDao
import com.inventorylibrary.app.dao.CatalogInOutDao
import com.inventorylibrary.app.dao.MemberDao
import com.inventorylibrary.app.entity.BookEntity
import com.inventorylibrary.app.entity.CatalogInOutEntity
import com.inventorylibrary.app.utils.InventoryDB
import java.util.*
import kotlin.collections.ArrayList

class CatalogInOutViewModel(
    private val inventoryDB: InventoryDB,
    private val bookDao: BookDao,
    private val memberDao: MemberDao,
    private val catalogInOutDao: CatalogInOutDao) : ViewModel() {

    val getBookList: LiveData<List<BookEntity>>
        get() = bookDao.getBookList()

    val selectedBook: LiveData<List<BookEntity>> get() = _selectedBook
    private val mutableSelectedBook = ArrayList<BookEntity>()
    private val _selectedBook = MutableLiveData<List<BookEntity>>()

    fun addSelectedBook(book: BookEntity){
        mutableSelectedBook.add(book)
        _selectedBook.value = mutableSelectedBook
    }

    suspend fun getMemberById(memberId: Int) = memberDao.getMemberById(memberId)

    suspend fun saveInOutTransaction(borrower: String, isCatalogIn: Boolean){
        inventoryDB.withTransaction {
            for (book in mutableSelectedBook){
                val catalogInOutEntity = CatalogInOutEntity(
                    catalogID = null,
                    catalogName = book.title,
                    date = Calendar.getInstance().timeInMillis,
                    image = book.coverImagePath,
                    borrower = borrower,
                    isCatalogIn = isCatalogIn
                )

                catalogInOutDao.addCatalogInOut(catalogInOutEntity)
            }
        }
    }
}