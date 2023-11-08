package com.example.btl_cnpm.ui.new_recipe

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btl_cnpm.data.repository.NewRecipeRepository
import com.example.btl_cnpm.model.CategoryType
import com.example.btl_cnpm.model.Procedure
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewRecipeViewModel @Inject constructor(private val repository: NewRecipeRepository) :
    ViewModel() {

    private var _downloadUri = MutableLiveData<UIState<String>>()
    val downloadUri get() = _downloadUri

    fun uploadImage(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.uploadImageToFirebase(uri) {
                _downloadUri.postValue(it)
            }
        }
    }

    fun getCategoryType(): MutableLiveData<UIState<List<CategoryType>>> {
        val mutableLiveData = MutableLiveData<UIState<List<CategoryType>>>()
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategoryType {
                mutableLiveData.postValue(it)
            }
        }
        return mutableLiveData
    }

    fun addNewRecipe(recipe: Recipe, lProcedure: List<Procedure>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.uploadNewRecipe(recipe) { addRecipeState ->
                when (addRecipeState) {
                    is UIState.Success -> {
                        addProcedure(addRecipeState.data, lProcedure)
                    }
                    is UIState.Failure -> {
                        Log.e("Dunno", addRecipeState.message.toString())
                    }
                }
            }
        }
    }

    private fun addProcedure(idRecipe: String, lProcedure: List<Procedure>) {
        viewModelScope.launch(Dispatchers.IO) {
            for (p in lProcedure) {
                launch {
                    repository.uploadProcedure(idRecipe, p) { addProcedureState ->
                        when (addProcedureState) {
                            is UIState.Success -> {
                                Log.d("Dunno", addProcedureState.data)
                            }
                            is UIState.Failure -> {
                                Log.e("Dunno", addProcedureState.message.toString())
                            }
                        }
                    }
                }
            }
        }
    }
}