package com.example.btl_cnpm.ui.splash

import androidx.lifecycle.ViewModel
import com.example.btl_cnpm.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {
}