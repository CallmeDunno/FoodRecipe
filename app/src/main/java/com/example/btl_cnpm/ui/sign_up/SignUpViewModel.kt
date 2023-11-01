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
    fun signUp(email: String, password: String): MutableLiveData<UIState<ArrayList<String>>> {
        val mutableLiveData = MutableLiveData<UIState<ArrayList<String>>>()
        viewModelScope.launch(Dispatchers.IO) {
            repository.signUp(email, password) {
                mutableLiveData.postValue(it)
            }
        }
        return mutableLiveData
    }

    fun createUser(id: String, username: String, email: String, password: String, img: String): MutableLiveData<UIState<String>> {
        val mutableLiveData = MutableLiveData<UIState<String>>()
        viewModelScope.launch(Dispatchers.IO) {
            repository.createUserCloud(id, username, email, password, img) {
                mutableLiveData.postValue(it)
            }
        }
        return mutableLiveData
    }
}