package com.example.btl_cnpm.data.local.RecipeLocal

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeLocalDao {
    @Query("SELECT * FROM RecipeLocal")
    fun getAllRecipes() : LiveData<List<RecipeLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipeLocal: RecipeLocal)
}