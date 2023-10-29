package com.example.btl_cnpm.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btl_cnpm.data.repository.HomeRepository
import com.example.btl_cnpm.data.repository.LoginRepository
import com.example.btl_cnpm.model.Category
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {
    fun getCategory(): MutableLiveData<ArrayList<Category>> {
        val mutableLiveData = MutableLiveData<ArrayList<Category>>()
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategoryList {
                mutableLiveData.postValue(it)
            }
        }
        return mutableLiveData
    }

    fun getRecipes(): MutableLiveData<ArrayList<Recipe>> {
        val mutableLiveData = MutableLiveData<ArrayList<Recipe>>()
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRecipeList {
                mutableLiveData.postValue(it)
            }
        }
        return mutableLiveData
    }
}