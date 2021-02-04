package com.inventorylibrary.app.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.inventorylibrary.app.entity.MultimediaEntity

@Dao
interface MultimediaDao {

    @Query("SELECT * FROM multimedia")
    fun getMultimediaList(): LiveData<List<MultimediaEntity>>

    @Insert
    suspend fun addMultimedia(multimedia: MultimediaEntity)

    @Query("SELECT * FROM multimedia WHERE multimediaID = :multimediaId LIMIT 1")
    suspend fun getMultimediaById(multimediaId: Int): MultimediaEntity?
}