package com.example.btl_cnpm.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BookmarkLocal::class], version = 1, exportSchema = true)
abstract class BookmarkLocalDatabase : RoomDatabase() {
    abstract fun bookmarkLocalDao(): BookmarkLocalDao

    companion object {
        @Volatile
        private var INSTANCE: BookmarkLocalDatabase? = null

        fun getDatabase(context: Context): BookmarkLocalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookmarkLocalDatabase::class.java,
                    "Bookmark"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}