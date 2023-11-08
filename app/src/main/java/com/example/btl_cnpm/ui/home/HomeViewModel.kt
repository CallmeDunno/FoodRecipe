package com.example.btl_cnpm.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btl_cnpm.data.repository.HomeRepository
import com.example.btl_cnpm.model.CategoryType
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.model.User
import com.example.btl_cnpm.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {
    fun getCategory(): MutableLiveData<UIState<ArrayList<CategoryType>>> {
        val mutableLiveData = MutableLiveData<UIState<ArrayList<CategoryType>>>()
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategoryList {
                mutableLiveData.postValue(it)
            }
        }
        return mutableLiveData
    }

    fun getRecipes(): MutableLiveData<UIState<ArrayList<Recipe>>> {
        val mutableLiveData = MutableLiveData<UIState<ArrayList<Recipe>>>()
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRecipeList {
                mutableLiveData.postValue(it)
            }
        }
        return mutableLiveData
    }

    fun getUser(id: String): MutableLiveData<UIState<User>> {
        val mutableLiveData = MutableLiveData<UIState<User>>()
        viewModelScope.launch(Dispatchers.Default) {
            repository.getUser(id) {
                mutableLiveData.postValue(it)
            }
        }
        return mutableLiveData
    }

    fun getRecipeUserList(): MutableLiveData<UIState<HashMap<Recipe, User>>> {
        val mutableLiveData = MutableLiveData<UIState<HashMap<Recipe, User>>>()
        viewModelScope.launch(Dispatchers.Default) {
            repository.getRecipeUserList {
                mutableLiveData.postValue(it)
            }
        }
        return mutableLiveData
    }
}