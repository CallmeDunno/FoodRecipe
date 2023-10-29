package com.example.btl_cnpm.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class NewRecipeRepository @Inject constructor(
    private val fAuth: FirebaseAuth,
    private val fFireStore: FirebaseFirestore,
    private val fStorage: FirebaseStorage
) {

}