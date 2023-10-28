package com.example.btl_cnpm.ui.sign_in

import androidx.lifecycle.ViewModel
import com.example.btl_cnpm.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {
}