package com.example.btl_cnpm.data.local.RecipeLocal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.btl_cnpm.data.local.BookmarkLocal
import com.example.btl_cnpm.data.local.BookmarkLocalDao

@Database(entities = [RecipeLocal::class], version = 1, exportSchema = true)
abstract class RecipeLocalDatabase: RoomDatabase() {
    abstract fun recipeLocalDao(): RecipeLocalDao

    companion object {
        private var INSTANCE: RecipeLocalDatabase? = null

        fun getInstance(context: Context): RecipeLocalDatabase? {
            if (INSTANCE == null) {
                synchronized(RecipeLocalDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, RecipeLocalDatabase::class.java, "RecipeLocal").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}