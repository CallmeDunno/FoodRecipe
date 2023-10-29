package com.example.btl_cnpm.ui.sign_up

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btl_cnpm.data.repository.LoginRepository
import com.example.btl_cnpm.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {
    fun signUp(username: String, email: String, password: String): MutableLiveData<UIState<String>> {
        val mutableLiveData = MutableLiveData<UIState<String>>()
        viewModelScope.launch(Dispatchers.Main) {
            repository.signUp(username, email, password) {
                mutableLiveData.postValue(it)
            }
        }
        return mutableLiveData
    }
}