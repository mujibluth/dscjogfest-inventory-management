package com.inventorylibrary.app.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "member")
data class MemberEntity(
    @PrimaryKey(autoGenerate = true)
    val memberID: Int? = null,
    val name: String,
    val birthPlace: String,
    val address: String,
    val sex: Int,
    val job: String,
    val phone: String,
    val email: String?,
    val profilePicture: String?,
    val identityPicture: String?,
    val joinDate: Long
)