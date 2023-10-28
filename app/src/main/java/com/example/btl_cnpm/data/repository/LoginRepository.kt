package com.example.btl_cnpm.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class LoginRepository @Inject constructor(private val fAuth: FirebaseAuth, private val fFireStore: FirebaseFirestore) {


}