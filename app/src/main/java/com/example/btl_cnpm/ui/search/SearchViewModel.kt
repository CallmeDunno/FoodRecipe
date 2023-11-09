package com.example.btl_cnpm.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btl_cnpm.data.local.BookmarkLocal
import com.example.btl_cnpm.data.local.RecipeLocal.RecipeLocal
import com.example.btl_cnpm.data.local.RecipeLocal.RecipeLocalRepository
import com.example.btl_cnpm.data.repository.HomeRepository
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
class SearchViewModel @Inject constructor(private val repository: HomeRepository, private val localRepository: RecipeLocalRepository) : ViewModel() {

    fun getCategory(): MutableLiveData<UIState<ArrayList<Category>>> {
        val mutableLiveData = MutableLiveData<UIState<ArrayList<Category>>>()
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

    fun getUsers(): MutableLiveData<UIState<ArrayList<User>>> {
        val mutableLiveData = MutableLiveData<UIState<ArrayList<User>>>()
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUserList {
                mutableLiveData.postValue(it)
            }
        }
        return mutableLiveData
    }

    fun getSearchedRecipes(idUser: String) =
            localRepository.getAllRecipes(idUser)

    fun insertRecipeLocal(recipe: RecipeLocal) {
        viewModelScope.launch(Dispatchers.IO) {
            localRepository.insertRecipe(recipe)
        }
    }

}