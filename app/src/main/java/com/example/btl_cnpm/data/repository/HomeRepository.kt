package com.example.btl_cnpm.data.repository

import android.util.Log
import com.example.btl_cnpm.model.Category
import com.example.btl_cnpm.model.Recipe
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject


class HomeRepository @Inject constructor(private val fAuth: FirebaseAuth, private val fFireStore: FirebaseFirestore) {
    private var listCategory = arrayListOf<Category>()

    private var listRecipe = arrayListOf<Recipe>()

    fun getCategoryList(result: (ArrayList<Category>) -> Unit) {
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
                    result.invoke(listCategory)
                } else {
                    Log.d("category", "get fail")
                }
            }
    }

    fun getRecipeList(result: (ArrayList<Recipe>) -> Unit) {
        listRecipe.clear()
        fFireStore.collection("Recipe")
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    for (document in it.result) {
                        val id = document.id
                        val name = document.toObject(Recipe::class.java).name
                        val idCategory = document.toObject(Recipe::class.java).idCategory
                        val idUser = document.toObject(Recipe::class.java).idUser
                        val ingredient = document.toObject(Recipe::class.java).ingredient
                        val date = document.toObject(Recipe::class.java).date
                        val image = document.toObject(Recipe::class.java).image
                        val timer = document.toObject(Recipe::class.java).timer
                        listRecipe.add(Recipe(id, name, idCategory, idUser, ingredient, date, image, timer))
                    }
                    result.invoke(listRecipe)
                } else {
                    Log.d("recipe", "fail")
                }
            }
    }
}