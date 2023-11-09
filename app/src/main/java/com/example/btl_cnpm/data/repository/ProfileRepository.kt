package com.example.btl_cnpm.data.repository

import android.util.Log
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.model.User
import com.example.btl_cnpm.utils.UIState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val fFireStore: FirebaseFirestore) {
    private var listRecipe = arrayListOf<Recipe>()
    private var listUser = arrayListOf<User>()
    private var userRecipeMap = hashMapOf<User, String>()

    fun getMyRecipeList(id: String, result: (UIState<ArrayList<Recipe>>) -> Unit) {
        listRecipe.clear()
        fFireStore.collection("Recipe")
            .whereEqualTo("idUser", id)
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
                        val rate = document.toObject(Recipe::class.java).rate
                        listRecipe.add(Recipe(id, name, idCategoryType, idUser, ingredient, date, image, timer, rate))
                    }
                    result.invoke(UIState.Success(listRecipe))
                } else {
                    result.invoke(UIState.Failure(it.exception?.message))
                }
            }
    }



    fun getUser(id: String, result: (UIState<User>) -> Unit) {
        listUser.clear()
        fFireStore.collection("User")
            .whereEqualTo("id", id)
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    for(doc in it.result) {
                        val id = doc.toObject(User::class.java).id
                        val username = doc.toObject(User::class.java).username
                        val bio = doc.toObject(User::class.java).bio
                        val image = doc.toObject(User::class.java).image
                        listUser.add(User(id, username, bio, image))
                    }
                    if(listUser.isNotEmpty()) {
                        result.invoke(UIState.Success(listUser[0]))
                    }
                    else {
                        result.invoke(UIState.Failure(it.exception?.message))
                    }
                } else {
                    result.invoke(UIState.Failure(it.exception?.message))
                }
            }
    }

    fun getUserId(id: String, result: (UIState<Map.Entry<User, String>>) -> Unit) {
        userRecipeMap.clear()
        fFireStore.collection("User")
            .whereEqualTo("id", id)
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    for(doc in it.result) {
                        val idUser = doc.id
                        val id = doc.toObject(User::class.java).id
                        val username = doc.toObject(User::class.java).username
                        val bio = doc.toObject(User::class.java).bio
                        val image = doc.toObject(User::class.java).image
                        userRecipeMap[User(id, username, bio, image)] = idUser
                    }
                    if(listUser.isNotEmpty()) {
                        result.invoke(UIState.Success(userRecipeMap.entries.first()))
                    }
                    else {
                        result.invoke(UIState.Failure(it.exception?.message))
                    }
                } else {
                    result.invoke(UIState.Failure(it.exception?.message))
                }
            }
    }

    fun updateUser(id: String, username: String, bio: String, image: String, result: (UIState<String>) -> Unit) {
        fFireStore.collection("User")
            .document(id)
            .update("username", username,
                    "bio", bio,
            "image", image)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    result.invoke(UIState.Success("Update successfully"))
                } else {
                    result.invoke(UIState.Failure(it.exception?.message))
                }
            }
    }
}