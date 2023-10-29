package com.example.btl_cnpm.data.repository

import com.example.btl_cnpm.utils.UIState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject


class LoginRepository @Inject constructor(private val fAuth: FirebaseAuth, private val fFireStore: FirebaseFirestore) {

    fun signIn(email: String, password: String, result: (UIState<String>) -> Unit) {
        fAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { result.invoke(UIState.Success(it.result.user?.uid.toString())) }
            .addOnFailureListener { result.invoke(UIState.Failure(it.message.toString())) }
    }

}