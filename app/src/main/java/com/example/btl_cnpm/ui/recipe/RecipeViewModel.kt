package com.example.btl_cnpm.ui.recipe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btl_cnpm.data.local.BookmarkLocal
import com.example.btl_cnpm.data.repository.RecipeRepository
import com.example.btl_cnpm.model.Procedure
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.model.User
import com.example.btl_cnpm.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val repository: RecipeRepository) : ViewModel() {

    private val _userMutableLiveData = MutableLiveData<UIState<User>>()
    val userMutableLiveData get() = _userMutableLiveData
    private val _procedureMutableLiveData = MutableLiveData<UIState<List<Procedure>>>()
    val procedureMutableLiveData get() = _procedureMutableLiveData
    private val _stateRate = MutableLiveData<UIState<String>>()
    val stateRate get() = _stateRate

    fun getRecipe(id: String): MutableLiveData<UIState<Recipe>> {
        val mutableLiveData = MutableLiveData<UIState<Recipe>>()
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRecipe(id) {
                mutableLiveData.postValue(it)
            }
        }
        return mutableLiveData
    }

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

    fun insertRate(idRecipe: String, idUser: String, rate: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertRate(idRecipe, idUser, rate) { insertRateState ->
                when (insertRateState) {
                    is UIState.Success -> {
                        calcRate(idRecipe, insertRateState)
                    }
                    is UIState.Failure -> {_stateRate.postValue(insertRateState)}
                }
            }
        }
    }

    fun checkExistsRateByUser(idRecipe: String, idUser: String) : MutableLiveData<UIState<Pair<Int, String>>>{
        val mutableLiveData = MutableLiveData<UIState<Pair<Int, String>>>()
        viewModelScope.launch(Dispatchers.IO) {
            repository.checkExistsRateByUser(idRecipe, idUser){
                mutableLiveData.postValue(it)
            }
        }
        return mutableLiveData
    }

    fun updateRate(idRecipe: String, idRate: String, rating: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRate(idRate, rating){ updateRateState ->
                when (updateRateState) {
                    is UIState.Success -> {calcRate(idRecipe, updateRateState)}
                    is UIState.Failure -> {}
                }
            }
        }
    }

    fun saveToBookmark(bookmarkLocal: BookmarkLocal){
        viewModelScope.launch(Dispatchers.IO) {
            val def = async {
                return@async repository.isExistsItem(bookmarkLocal.idRecipe, bookmarkLocal.idUser)
            }
            val isCheck = def.await()
            launch {
                if (isCheck == 0) repository.insertItemToLocal(bookmarkLocal)
            }
        }
    }

    private fun calcRate(idRecipe: String, result: (UIState<String>)){
        viewModelScope.launch(Dispatchers.IO){
            repository.getAllRateById(idRecipe){ getAllItemState ->
                when(getAllItemState) {
                    is UIState.Success -> {
                        repository.updateRateInRecipe(idRecipe, getAllItemState.data){updateRateState ->
                            when(updateRateState){
                                is UIState.Success -> {_stateRate.postValue(result)}
                                is UIState.Failure -> {_stateRate.postValue(result)}
                            }
                        }
                    }
                    is UIState.Failure -> {_stateRate.postValue(result)}
                }
            }
        }
    }

}