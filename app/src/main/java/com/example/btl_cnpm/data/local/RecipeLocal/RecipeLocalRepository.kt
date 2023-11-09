package com.example.btl_cnpm.data.local.RecipeLocal

import javax.inject.Inject

class RecipeLocalRepository @Inject constructor(private val dao: RecipeLocalDao) {
    fun getAllRecipes(idUser: String) = dao.getAllRecipes(idUser)

    fun insertRecipe(recipeLocal: RecipeLocal) = dao.insertRecipe(recipeLocal)
}