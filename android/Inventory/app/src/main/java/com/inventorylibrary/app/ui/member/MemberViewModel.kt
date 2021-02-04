package com.inventorylibrary.app.ui.member

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inventorylibrary.app.dao.MemberDao
import com.inventorylibrary.app.entity.MemberEntity

class MemberViewModel(private val memberDao: MemberDao) : ViewModel() {

    val memberList: LiveData<List<MemberEntity>>
        get() = memberDao.getMemberList()
}