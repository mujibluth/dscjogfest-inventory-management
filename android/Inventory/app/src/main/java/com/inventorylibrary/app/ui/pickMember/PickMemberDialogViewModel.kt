package com.inventorylibrary.app.ui.pickMember

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inventorylibrary.app.dao.MemberDao
import com.inventorylibrary.app.entity.MemberEntity

class PickMemberDialogViewModel(private val memberDao: MemberDao) : ViewModel() {

    val memberList: LiveData<List<MemberEntity>>
        get() = memberDao.getMemberList()
}