package com.inventorylibrary.app.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "catalogInOut")
data class CatalogInOutEntity(
    @PrimaryKey(autoGenerate = true)
    val catalogID: Int? = null,
    val catalogName: String,
    val date: Long,
    val image: String?,
    val borrower: String,
    val isCatalogIn: Boolean
)