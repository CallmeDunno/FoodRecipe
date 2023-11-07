package com.example.btl_cnpm.data.repository

import android.util.Log
import com.example.btl_cnpm.model.Category
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.model.User
import com.example.btl_cnpm.utils.UIState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class HomeRepository @Inject constructor(
    private val fAuth: FirebaseAuth,
    private val fFireStore: FirebaseFirestore
) {
    private var listCategory = arrayListOf<Category>()

    private var listRecipe = arrayListOf<Recipe>()
    private var listUser2 = arrayListOf<User>()
    private var listUser = arrayListOf<User>()

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


    fun getUser(userId: String, result: (UIState<User>) -> Unit) {
        fFireStore.collection("User")
            .whereEqualTo("id", userId)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (doc in it.result) {
                        val id = doc.toObject<User>().id
                        val username = doc.toObject<User>().username
                        val image = doc.toObject<User>().image
                        result.invoke(UIState.Success(User(id, username, image)))
                        break
                    }
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

    fun map() {

    }
}