package com.example.btl_cnpm.data.repository

import com.example.btl_cnpm.model.CategoryType
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.model.User
import com.example.btl_cnpm.utils.UIState
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class SearchRepository @Inject constructor(private val fFireStore: FirebaseFirestore) {
    private var listCategoryType = arrayListOf<CategoryType>()
    private var listRecipe = arrayListOf<Recipe>()
    private var listUser = arrayListOf<User>()

    fun getCategoryTypeList(result: (UIState<ArrayList<CategoryType>>) -> Unit) {
        listCategoryType.clear()
        fFireStore.collection("CategoryType")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result) {
                        val id = document.id
                        val name = document.toObject(CategoryType::class.java).name
                        listCategoryType.add(CategoryType(id, name))
                    }
                    result.invoke(UIState.Success(listCategoryType))
                } else {
                    result.invoke(UIState.Failure(it.exception?.message))
                }
            }
    }

    fun getRecipeList(result: (UIState<ArrayList<Recipe>>) -> Unit) {
        listRecipe.clear()
        fFireStore.collection("Recipe")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result) {
                        val id = document.id
                        val name = document.toObject(Recipe::class.java).name
                        val idCategoryType = document.toObject(Recipe::class.java).idCategoryType
                        val idUser = document.toObject(Recipe::class.java).idUser
                        val ingredient = document.toObject(Recipe::class.java).ingredient
                        val date = document.toObject(Recipe::class.java).date
                        val image = document.toObject(Recipe::class.java).image
                        val timer = document.toObject(Recipe::class.java).timer
                        val rate = document.toObject(Recipe::class.java).rate
                        listRecipe.add(
                            Recipe(
                                id,
                                name,
                                idCategoryType,
                                idUser,
                                ingredient,
                                date,
                                image,
                                timer,
                                rate
                            )
                        )
                    }
                    result.invoke(UIState.Success(listRecipe))
                } else {
                    result.invoke(UIState.Failure(it.exception?.message))
                }
            }
    }

    fun getUserList(result: (UIState<ArrayList<User>>) -> Unit) {
        listUser.clear()
        fFireStore.collection("User")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (doc in it.result) {
                        val id = doc.toObject(User::class.java).id
                        val username = doc.toObject(User::class.java).username
                        val image = doc.toObject(User::class.java).image
                        listUser.add(User(id, username, image))
                    }
                    if (listUser.isNotEmpty()) {
                        result.invoke(UIState.Success(listUser))
                    } else {
                        result.invoke(UIState.Failure(it.exception?.message))
                    }
                } else {
                    result.invoke(UIState.Failure(it.exception?.message))
                }
            }
    }
}