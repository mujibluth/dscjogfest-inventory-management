package com.inventorylibrary.app.ui.addMultimedia

import androidx.lifecycle.ViewModel
import com.inventorylibrary.app.dao.MultimediaDao
import com.inventorylibrary.app.entity.MultimediaEntity

class AddMultimediaViewModel(private val multimediaDao: MultimediaDao) : ViewModel() {

    suspend fun addMultimedia(multimedia: MultimediaEntity) = multimediaDao.addMultimedia(multimedia)
}