package com.inventorylibrary.app.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.inventorylibrary.app.entity.CatalogInOutEntity

@Dao
interface CatalogInOutDao {

    @Query("SELECT * FROM catalogInOut")
    fun getAllCatalog(): LiveData<List<CatalogInOutEntity>>

    @Insert
    suspend fun addCatalogInOut(catalogInOutEntity: CatalogInOutEntity)
}