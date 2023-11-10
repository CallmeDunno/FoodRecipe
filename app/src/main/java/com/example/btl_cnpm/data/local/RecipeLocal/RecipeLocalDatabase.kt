package com.example.btl_cnpm.data.local.RecipeLocal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RecipeLocal::class], version = 1, exportSchema = true)
abstract class RecipeLocalDatabase: RoomDatabase() {
    abstract fun recipeLocalDao(): RecipeLocalDao

}