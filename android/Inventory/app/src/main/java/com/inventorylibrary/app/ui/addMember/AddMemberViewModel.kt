package com.inventorylibrary.app.ui.addMember

import androidx.lifecycle.ViewModel
import com.inventorylibrary.app.dao.MemberDao
import com.inventorylibrary.app.entity.MemberEntity

class AddMemberViewModel(private val memberDao: MemberDao) : ViewModel() {

    suspend fun addMember(member: MemberEntity) = memberDao.addMember(member)
}