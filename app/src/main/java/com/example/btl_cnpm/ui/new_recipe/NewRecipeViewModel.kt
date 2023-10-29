package com.example.btl_cnpm.ui.new_recipe

import androidx.lifecycle.ViewModel
import com.example.btl_cnpm.data.repository.NewRecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewRecipeViewModel @Inject constructor(private val repository: NewRecipeRepository) : ViewModel() {
}