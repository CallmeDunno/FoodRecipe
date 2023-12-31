package com.example.btl_cnpm.ui.sign_in

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
class SignInViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {

    fun signIn(email: String, password: String): MutableLiveData<UIState<String>> {
        val mutableLiveData = MutableLiveData<UIState<String>>()
        viewModelScope.launch(Dispatchers.IO) {
            repository.signIn(email, password) {
                mutableLiveData.postValue(it)
            }
        }
        return mutableLiveData
    }

}