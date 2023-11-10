package com.example.btl_cnpm.data.repository

import android.net.Uri
import android.util.Log
import com.example.btl_cnpm.model.CategoryType
import com.example.btl_cnpm.model.Procedure
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.utils.UIState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NewRecipeRepository @Inject constructor(
    private val fAuth: FirebaseAuth,
    private val fFireStore: FirebaseFirestore,
    private val fStorage: FirebaseStorage
) {

    fun uploadImageToFirebase(uri: Uri, result: (UIState<String>) -> Unit) {
        val format = SimpleDateFormat("dd_MM_yyyy_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val filename = format.format(now)

        fStorage.reference.child("image/$filename").putFile(uri)
            .addOnSuccessListener {
                it.storage
                    .downloadUrl
                    .addOnCompleteListener { downloadTask ->
                        if (downloadTask.isSuccessful) {
                            Log.d("Dunno", downloadTask.result.toString())
                            result.invoke(UIState.Success(downloadTask.result.toString()))
                        } else {
                            result.invoke(UIState.Failure("Download Uri Failure"))
                        }
                    }
                    .addOnFailureListener { downloadFail ->
                        result.invoke(UIState.Failure(downloadFail.message.toString()))
                    }
            }
            .addOnFailureListener { result.invoke(UIState.Failure(it.message.toString())) }
    }

    fun uploadNewRecipe(recipe: Recipe, result: (UIState<String>) -> Unit) {
        fFireStore.collection("Recipe").add(recipe)
            .addOnCompleteListener {
                if (it.isSuccessful) result.invoke(UIState.Success(it.result.id))
                else result.invoke(UIState.Failure(it.exception?.message.toString()))
            }
            .addOnFailureListener { result.invoke(UIState.Failure(it.message.toString())) }
    }


    fun uploadProcedure(idRecipe: String, procedure: Procedure, result: (UIState<String>) -> Unit) {
        fFireStore
            .collection("Recipe")
            .document(idRecipe)
            .collection("Procedure")
            .add(procedure)
            .addOnCompleteListener {
                if (it.isSuccessful) result.invoke(UIState.Success("Add Procedure Successful"))
                else result.invoke(UIState.Failure("Add Procedure Failure"))
            }
            .addOnFailureListener { result.invoke(UIState.Failure(it.message.toString())) }
    }

    fun getCategoryType(result: (UIState<List<CategoryType>>) -> Unit) {
        fFireStore.collection("CategoryType")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val list = ArrayList<CategoryType>()
                    for (d in it.result) {
                        val id = d.id
                        val name = d.data["name"].toString()
                        list.add(CategoryType(id, name))
                    }
                    result.invoke(UIState.Success(list))
                } else result.invoke(UIState.Failure(it.exception?.message.toString()))
            }
            .addOnFailureListener { result.invoke(UIState.Failure(it.message.toString())) }
    }

}