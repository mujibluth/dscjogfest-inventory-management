package com.inventorylibrary.app.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.inventorylibrary.app.entity.BookEntity

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBook(book: BookEntity)

    @Query("SELECT * FROM book")
    fun getBookList(): LiveData<List<BookEntity>>

    @Query("SELECT * FROM book WHERE bookID = :bookID LIMIT 1")
    suspend fun getBookById(bookID: Int): BookEntity?

    @Query("SELECT bookID FROM book")
    fun getBookSize(): LiveData<List<Int>>
}