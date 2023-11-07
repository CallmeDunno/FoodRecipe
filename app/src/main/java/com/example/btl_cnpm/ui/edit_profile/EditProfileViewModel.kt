package com.example.btl_cnpm.ui.edit_profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btl_cnpm.data.repository.HomeRepository
import com.example.btl_cnpm.data.repository.ProfileRepository
import com.example.btl_cnpm.model.User
import com.example.btl_cnpm.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(private val repository: ProfileRepository) : ViewModel() {
    fun getUserId(id: String): MutableLiveData<UIState<Map.Entry<User, String>>> {
        val user = MutableLiveData<UIState<Map.Entry<User, String>>>()
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUserId(id) {
                user.postValue(it)
            }
        }
        return user
    }

    fun updateUser(id: String, username: String, bio: String, image: String): MutableLiveData<UIState<String>> {
        val user = MutableLiveData<UIState<String>>()
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(id, username, bio, image) {
                user.postValue(it)
            }
        }
        return user
    }
}