package com.inventorylibrary.app.ui.multimediaDetail

import androidx.lifecycle.ViewModel
import com.inventorylibrary.app.dao.MultimediaDao

class MultimediaDetailViewModel(private val multimediaDao: MultimediaDao) : ViewModel() {

    suspend fun getMultimediaById(multimediaId: Int) = multimediaDao.getMultimediaById(multimediaId)
}