package com.example.btl_cnpm.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btl_cnpm.data.repository.ProfileRepository
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.model.User
import com.example.btl_cnpm.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: ProfileRepository) : ViewModel() {
    fun getUser(id: String): MutableLiveData<UIState<User>> {
        val user = MutableLiveData<UIState<User>>()
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUser(id) {
                    user.postValue(it)
                }
            }
        return user
        }
    fun getMyRecipes(id: String): MutableLiveData<UIState<ArrayList<Recipe>>> {
        val listRecipe = MutableLiveData<UIState<ArrayList<Recipe>>>()
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMyRecipeList(id) {
                listRecipe.postValue(it)
            }
        }
        return listRecipe
    }
}