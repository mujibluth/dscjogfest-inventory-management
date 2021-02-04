package com.inventorylibrary.app.ui.memberDetail

import androidx.lifecycle.ViewModel
import com.inventorylibrary.app.dao.MemberDao

class MemberDetailViewModel(private val memberDao: MemberDao) : ViewModel() {

    suspend fun getMemberById(memberId: Int) = memberDao.getMemberById(memberId)
}