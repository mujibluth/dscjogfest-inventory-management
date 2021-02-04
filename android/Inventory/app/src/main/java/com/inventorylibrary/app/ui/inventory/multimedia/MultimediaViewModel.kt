package com.inventorylibrary.app.ui.inventory.multimedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inventorylibrary.app.dao.MultimediaDao
import com.inventorylibrary.app.entity.MultimediaEntity

class MultimediaViewModel(private val multimediaDao: MultimediaDao) : ViewModel() {

    val multimediaList: LiveData<List<MultimediaEntity>>
        get() = multimediaDao.getMultimediaList()
}