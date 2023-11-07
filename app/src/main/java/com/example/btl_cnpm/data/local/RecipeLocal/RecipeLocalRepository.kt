package com.example.btl_cnpm.data.local.RecipeLocal

import com.example.btl_cnpm.data.local.BookmarkLocal
import com.example.btl_cnpm.data.local.BookmarkLocalDao
import javax.inject.Inject

class RecipeLocalRepository @Inject constructor(private val dao: RecipeLocalDao) {
    fun getAllRecipes() = dao.getAllRecipes()

    fun insertRecipe(recipeLocal: RecipeLocal) = dao.insertRecipe(recipeLocal)
}