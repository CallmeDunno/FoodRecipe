package com.example.btl_cnpm.ui.forgot_pasword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.btl_cnpm.data.repository.LoginRepository
import com.example.btl_cnpm.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {
    private val _isResetEmailSent: MutableLiveData<Boolean> = MutableLiveData()
    val isResetEmailSent: LiveData<Boolean> get() = _isResetEmailSent

    fun sendResetPasswordEmail(email: String) {
        repository.forgotPassword(email) { uiState ->
            when (uiState) {
                is UIState.Success -> _isResetEmailSent.value = true
                is UIState.Failure -> _isResetEmailSent.value = false
            }
        }
    }


}