package com.inventorylibrary.app.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "multimedia")
data class MultimediaEntity(
    @PrimaryKey(autoGenerate = true)
    val multimediaID: Int? = null,
    val title: String,
    val publisher: String,
    val yearPublish: String,
    val stock: Int,
    val rack: String,
    val description: String?,
    val image: String?
)