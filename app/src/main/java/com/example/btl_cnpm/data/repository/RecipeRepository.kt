package com.example.btl_cnpm.data.repository

import com.example.btl_cnpm.model.Procedure
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.model.User
import com.example.btl_cnpm.utils.UIState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import javax.inject.Inject

class RecipeRepository @Inject constructor(private val fFireStore: FirebaseFirestore) {

    fun getRecipe(id: String, result: (UIState<Recipe>) -> Unit) {
        fFireStore.collection("Recipe")
            .document(id)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    val documentId = document.id
                    val name = document.toObject(Recipe::class.java)!!.name
                    val idCategoryType = document.toObject(Recipe::class.java)!!.idCategoryType
                    val idUser = document.toObject(Recipe::class.java)!!.idUser
                    val ingredient = document.toObject(Recipe::class.java)!!.ingredient
                    val date = document.toObject(Recipe::class.java)!!.date
                    val image = document.toObject(Recipe::class.java)!!.image
                    val timer = document.toObject(Recipe::class.java)!!.timer
                    val recipe =
                        Recipe(
                            documentId,
                            name,
                            idCategoryType,
                            idUser,
                            ingredient,
                            date,
                            image,
                            timer
                        )
                    result.invoke(UIState.Success(recipe))
                } else {
                    result.invoke(UIState.Failure(task.exception.toString()))
                }
            }
            .addOnFailureListener { result.invoke(UIState.Failure(it.message.toString())) }
    }

    fun getUser(id: String, result: (UIState<User>) -> Unit) {
        fFireStore.collection("User").whereEqualTo("id", id).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result

                } else result.invoke(UIState.Failure(task.exception.toString()))
            }
            .addOnFailureListener { result.invoke(UIState.Failure(it.message.toString())) }
    }

    fun getProcedure(id: String, result: (UIState<List<Procedure>>) -> Unit) {
        fFireStore.collection("Recipe").document(id).collection("Procedure").get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val lProcedure = ArrayList<Procedure>()
                    for (document in it.result) {
                        val index = document.toObject<Procedure>().index
                        val content = document.toObject<Procedure>().content
                        val procedure = Procedure(index, content)
                        lProcedure.add(procedure)
                    }
                    result.invoke(UIState.Success(lProcedure))
                } else result.invoke(UIState.Failure(it.exception.toString()))
            }
            .addOnFailureListener { result.invoke(UIState.Failure(it.message.toString())) }
    }

}