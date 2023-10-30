package com.example.btl_cnpm.data.repository

import android.util.Log
import com.example.btl_cnpm.model.Category
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.utils.UIState
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class SearchRepository @Inject constructor(private val fFireStore: FirebaseFirestore) {
    private var listCategory = arrayListOf<Category>()

    private var listRecipe = arrayListOf<Recipe>()

    fun getCategoryList(result: (UIState<ArrayList<Category>>) -> Unit) {
        listCategory.clear()
        fFireStore.collection("CategoryType")
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    for(document in it.result) {
                        val id = document.id
                        val name = document.toObject(Category::class.java).name
                        listCategory.add(Category(id, name))
                    }
                    result.invoke(UIState.Success(listCategory))
                } else {
                    Log.d("category", "get fail")
                }
            }
    }

    fun getRecipeList(result: (UIState<ArrayList<Recipe>>) -> Unit) {
        listRecipe.clear()
        fFireStore.collection("Recipe")
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    for (document in it.result) {
                        val id = document.id
                        val name = document.toObject(Recipe::class.java).name
                        val idCategoryType = document.toObject(Recipe::class.java).idCategoryType
                        val idUser = document.toObject(Recipe::class.java).idUser
                        val ingredient = document.toObject(Recipe::class.java).ingredient
                        val date = document.toObject(Recipe::class.java).date
                        val image = document.toObject(Recipe::class.java).image
                        val timer = document.toObject(Recipe::class.java).timer
                        listRecipe.add(Recipe(id, name, idCategoryType, idUser, ingredient, date, image, timer))
                    }
                    result.invoke(UIState.Success(listRecipe))
                } else {
                    Log.d("recipe", "fail")
                }
            }
    }

    fun getRecipeByName(input: String, result: (UIState<ArrayList<Recipe>>) -> Unit) {
        listRecipe.clear()
        fFireStore.collection("Recipe")
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    for (document in it.result) {
                        val id = document.id
                        val name = document.toObject(Recipe::class.java).name
                        val idCategoryType = document.toObject(Recipe::class.java).idCategoryType
                        val idUser = document.toObject(Recipe::class.java).idUser
                        val ingredient = document.toObject(Recipe::class.java).ingredient
                        val date = document.toObject(Recipe::class.java).date
                        val image = document.toObject(Recipe::class.java).image
                        val timer = document.toObject(Recipe::class.java).timer
                        listRecipe.add(Recipe(id, name, idCategoryType, idUser, ingredient, date, image, timer))
                    }
                    result.invoke(UIState.Success(listRecipe))
                } else {
                    Log.d("recipe", "fail")
                }
            }
    }
}