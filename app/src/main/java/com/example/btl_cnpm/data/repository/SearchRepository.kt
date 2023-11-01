package com.example.btl_cnpm.data.repository

import android.util.Log
import com.example.btl_cnpm.model.Category
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.model.User
import com.example.btl_cnpm.utils.UIState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class SearchRepository @Inject constructor(private val fFireStore: FirebaseFirestore) {
    private var listCategory = arrayListOf<Category>()
    private var userRecipeMap = hashMapOf<Recipe, User>()

    private var listUser = arrayListOf<User>()
    private var listUser2 = arrayListOf<User>()

    fun getCategoryList(result: (UIState<ArrayList<Category>>) -> Unit) {
        listCategory.clear()
        fFireStore.collection("CategoryType")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result) {
                        val id = document.id
                        val name = document.toObject(Category::class.java).name
                        listCategory.add(Category(id, name))
                    }
                    result.invoke(UIState.Success(listCategory))
                } else {
                    result.invoke(UIState.Failure(it.exception?.message.toString()))
                }
            }
    }

    fun getRecipeByName(result: (UIState<HashMap<Recipe, User>>) -> Unit) {
        userRecipeMap.clear()
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
                        getUserByRecipe(idUser) {result ->
                            when(result) {
                                is UIState.Success -> {
                                    userRecipeMap[Recipe(id, name, idCategoryType, idUser, ingredient, date, image, timer)] = result.data
                                }
                                is UIState.Failure -> {
                                    result.message?.let {mes->
                                        Log.d("tung", mes)
                                    }
                                }
                            }
                        }
                    }
                    result.invoke(UIState.Success(userRecipeMap))
                } else {
                    result.invoke(UIState.Failure(it.exception?.message))
                }
            }
    }

    fun getUserByRecipe(id: String, result: (UIState<User>) -> Unit) {
        listUser2.clear()
        fFireStore.collection("User")
            .whereEqualTo("id", id)
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    for(doc in it.result) {
                        val id = doc.toObject(User::class.java).id
                        val username = doc.toObject(User::class.java).username
                        val image = doc.toObject(User::class.java).image
                        listUser2.add(User(id, username, image))
                    }
                    if(listUser2.isNotEmpty()) {
                        result.invoke(UIState.Success(listUser2[0]))
                    } else {
                        result.invoke(UIState.Failure("cant get user"))
                    }
                } else {
                    result.invoke(UIState.Failure("cant get user"))
                }
            }
    }
}