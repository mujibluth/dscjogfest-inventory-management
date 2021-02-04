package com.inventorylibrary.app.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.inventorylibrary.app.entity.MemberEntity

@Dao
interface MemberDao {

    @Query("SELECT * FROM member")
    fun getMemberList(): LiveData<List<MemberEntity>>

    @Query("SELECT * FROM member WHERE memberID = :memberId LIMIT 1")
    suspend fun getMemberById(memberId: Int): MemberEntity?

    @Insert
    suspend fun addMember(member: MemberEntity)

    @Query("SELECT memberID FROM member")
    fun getMemberSize(): LiveData<List<Int>>
}