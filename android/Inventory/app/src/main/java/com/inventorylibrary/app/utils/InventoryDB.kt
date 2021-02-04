package com.inventorylibrary.app.utils

import androidx.room.Database
import androidx.room.RoomDatabase
import com.inventorylibrary.app.dao.BookDao
import com.inventorylibrary.app.dao.CatalogInOutDao
import com.inventorylibrary.app.dao.MemberDao
import com.inventorylibrary.app.dao.MultimediaDao
import com.inventorylibrary.app.entity.BookEntity
import com.inventorylibrary.app.entity.CatalogInOutEntity
import com.inventorylibrary.app.entity.MemberEntity
import com.inventorylibrary.app.entity.MultimediaEntity

@Database(
    entities = [
        BookEntity::class,
        CatalogInOutEntity::class,
        MemberEntity::class,
        MultimediaEntity::class],
    version = 1
)
abstract class InventoryDB: RoomDatabase() {

    abstract fun bookDao(): BookDao

    abstract fun catalogInOutDao(): CatalogInOutDao

    abstract fun memberDao(): MemberDao

    abstract fun multimediaDao(): MultimediaDao
}