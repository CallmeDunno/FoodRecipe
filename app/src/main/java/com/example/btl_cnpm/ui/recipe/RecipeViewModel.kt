package com.example.btl_cnpm.ui.recipe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btl_cnpm.data.repository.RecipeRepository
import com.example.btl_cnpm.model.Procedure
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.model.User
import com.example.btl_cnpm.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val repository: RecipeRepository) : ViewModel() {

    fun getRecipe(id: String): MutableLiveData<UIState<Recipe>> {
        val mutableLiveData = MutableLiveData<UIState<Recipe>>()
        viewModelScope.launch {
            repository.getRecipe(id) {
                mutableLiveData.postValue(it)
            }
        }
        return mutableLiveData
    }

    private val _userMutableLiveData = MutableLiveData<UIState<User>>()
    val userMutableLiveData get() = _userMutableLiveData
    private val _procedureMutableLiveData = MutableLiveData<UIState<List<Procedure>>>()
    val procedureMutableLiveData get() = _procedureMutableLiveData

    fun getOtherElement(idUser: String, idRecipe: String) {
        viewModelScope.launch(Dispatchers.IO) {
            launch {
                repository.getUser(idUser) {
                    _userMutableLiveData.postValue(it)
                }
            }
            launch {
                repository.getProcedure(idRecipe) {
                    _procedureMutableLiveData.postValue(it)
                }
            }
        }
    }

}