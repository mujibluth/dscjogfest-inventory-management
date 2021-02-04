package com.inventorylibrary.app.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "book")
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val bookID: Int? = null,
    val title: String,
    val writer: String,
    val genre: String,
    val year: Int,
    val isbn: String,
    val stock: Int,
    val rack: String,
    val notes: String?,
    val coverImagePath: String?
): Parcelable