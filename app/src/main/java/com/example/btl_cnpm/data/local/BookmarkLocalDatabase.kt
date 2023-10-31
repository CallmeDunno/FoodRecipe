package com.example.btl_cnpm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BookmarkLocal::class], version = 1, exportSchema = true)
abstract class BookmarkLocalDatabase : RoomDatabase() {
    abstract fun bookmarkLocalDao(): BookmarkLocalDao

}