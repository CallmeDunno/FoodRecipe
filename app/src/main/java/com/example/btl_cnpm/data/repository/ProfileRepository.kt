package com.example.btl_cnpm.data.repository

import android.util.Log
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.model.User
import com.example.btl_cnpm.utils.UIState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val fAuth: FirebaseAuth, private val fFireStore: FirebaseFirestore) {
    private var listRecipe = arrayListOf<Recipe>()
    fun getUser(id: String, result: (UIState<User>) -> Unit) {
//        val userId = SharedPreferencesManager().getString(id)
        fFireStore.collection("User")
            .document(id)
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    val document = it.result
                    if(document != null) {
                        val id = document.id
                        val username = document.toObject(User::class.java)!!.username
                        val bio = document.toObject(User::class.java)!!.bio
                        result.invoke(UIState.Success(User(id, username, bio)))
                    }
                }
                else {
                    result.invoke(UIState.Failure("fail"))
                }
            }
    }

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
                        listRecipe.add(Recipe(id, name, idCategoryType, idUser, ingredient, date, image, timer))
                    }
                    result.invoke(UIState.Success(listRecipe))
                } else {
                    Log.d("recipe", "fail")
                }
            }
    }
}