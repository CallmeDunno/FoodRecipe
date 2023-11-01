package com.example.btl_cnpm.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btl_cnpm.data.repository.LoginRepository
import com.example.btl_cnpm.data.repository.SearchRepository
import com.example.btl_cnpm.model.Category
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.model.User
import com.example.btl_cnpm.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository) : ViewModel() {

    fun getCategory(): MutableLiveData<UIState<ArrayList<Category>>> {
        val mutableLiveData = MutableLiveData<UIState<ArrayList<Category>>>()
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategoryList {
                mutableLiveData.postValue(it)
            }
        }
        return mutableLiveData
    }

    fun getRecipeByName(): MutableLiveData<UIState<HashMap<Recipe, User>>> {
        val mutableLiveData = MutableLiveData<UIState<HashMap<Recipe, User>>>()
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRecipeByName {
                mutableLiveData.postValue(it)
            }
        }
        return mutableLiveData
    }
}