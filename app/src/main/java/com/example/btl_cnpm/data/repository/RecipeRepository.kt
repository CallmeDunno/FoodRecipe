package com.example.btl_cnpm.data.repository

import com.example.btl_cnpm.data.local.BookmarkLocal
import com.example.btl_cnpm.data.local.BookmarkLocalDao
import com.example.btl_cnpm.model.Procedure
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.model.User
import com.example.btl_cnpm.utils.UIState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import java.math.RoundingMode
import javax.inject.Inject

class RecipeRepository @Inject constructor(private val fFireStore: FirebaseFirestore, private val dao: BookmarkLocalDao) {

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
                    val rate = document.toObject(Recipe::class.java)!!.rate
                    val recipe = Recipe(
                        documentId, name, idCategoryType, idUser, ingredient, date,
                        image,
                        timer,
                        rate
                    )
                    result.invoke(UIState.Success(recipe))
                } else {
                    result.invoke(UIState.Failure(task.exception.toString()))
                }
            }
            .addOnFailureListener { result.invoke(UIState.Failure(it.message.toString())) }
    }

    fun getUser(id: String, result: (UIState<User>) -> Unit) {
        fFireStore.collection("User").whereEqualTo("id", id).limit(1).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (!document.isEmpty) {
                        var user = User()
                        for (u in document) {
                            val username = u.toObject<User>().username
                            val image = u.toObject<User>().image
                            result.invoke(UIState.Success(User(id, username, image)))
                            break
                        }
                    } else result.invoke(UIState.Failure("The information of user is empty!"))

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

    fun insertRate(
        idRecipe: String,
        idUser: String,
        rating: Int,
        result: (UIState<String>) -> Unit
    ) {
        val itemRate = hashMapOf(
            "idRecipe" to idRecipe,
            "idUser" to idUser,
            "rating" to rating
        )
        fFireStore
            .collection("Rate")
            .document().set(itemRate)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) result.invoke(UIState.Success("Rating Successful"))
                else result.invoke(UIState.Failure("Rating Failure"))
            }
            .addOnFailureListener { result.invoke(UIState.Failure(it.message.toString())) }
    }

    fun updateRate(
        idRate: String,
        rating: Int,
        result: (UIState<String>) -> Unit
    ) {
        fFireStore
            .collection("Rate")
            .document(idRate)
            .update("rating", rating)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) result.invoke(UIState.Success("Update Rating Successful"))
                else result.invoke(UIState.Failure("Update Rating Failure"))
            }
            .addOnFailureListener { result.invoke(UIState.Failure(it.message.toString())) }
    }

    fun getAllRateById(idRecipe: String, result: (UIState<Float>) -> Unit) {
        fFireStore
            .collection("Rate")
            .whereEqualTo("idRecipe", idRecipe)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    if (!it.result.isEmpty) {
                        var sum = 0
                        for (r in it.result) {
                            sum += r.data["rating"].toString().toInt()
                        }
                        val rate = (sum / it.result.size()).toFloat().toBigDecimal()
                            .setScale(1, RoundingMode.HALF_EVEN).toFloat()
                        result.invoke(UIState.Success(rate))
                    } else result.invoke(UIState.Failure("Not rate"))
                }
            }
            .addOnFailureListener { result.invoke(UIState.Failure(it.message.toString())) }
    }

    fun updateRateInRecipe(idRecipe: String, rate: Float, result: (UIState<Boolean>) -> Unit) {
        fFireStore
            .collection("Recipe")
            .document(idRecipe)
            .update("rate", rate)
            .addOnCompleteListener {
                if (it.isSuccessful) result.invoke(UIState.Success(true))
                else result.invoke(UIState.Failure("Update Rate In Recipe Failure"))
            }
            .addOnFailureListener { result.invoke(UIState.Failure(it.message.toString())) }
    }

    fun checkExistsRateByUser(idRecipe: String, idUser: String, result: (UIState<Pair<Int, String>>) -> Unit) {
        fFireStore.collection("Rate")
            .whereEqualTo("idUser", idUser)
            .whereEqualTo("idRecipe", idRecipe)
            .limit(1)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    if (it.result.size() > 0) {
                        val rate = it.result.documents[0].data?.get("rating").toString().toInt()
                        result.invoke(UIState.Success(Pair(rate, it.result.documents[0].id)))
                    } else result.invoke(UIState.Success(Pair(0, "")))
                } else result.invoke(UIState.Failure(it.exception.toString()))
            }
            .addOnFailureListener { result.invoke(UIState.Failure(it.message.toString())) }
    }

    fun insertItemToLocal(bookmarkLocal: BookmarkLocal) = dao.insertItemBookmark(bookmarkLocal)

    fun isExistsItem(idRecipe: String, idUser: String): Int {
        val x = dao.isExistsItem(idRecipe, idUser)
        return x.size
    }
}