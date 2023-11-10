package com.example.btl_cnpm.data.local.RecipeLocal

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeLocalDao {
    @Query("SELECT IdRecipe FROM RecipeLocal WHERE idUser = :idUser")
    fun getAllRecipes(idUser: String): LiveData<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipeLocal: RecipeLocal)
}