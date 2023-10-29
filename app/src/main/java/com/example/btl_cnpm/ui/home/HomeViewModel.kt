package com.example.btl_cnpm.ui.home

import androidx.lifecycle.ViewModel
import com.example.btl_cnpm.data.repository.HomeRepository
import com.example.btl_cnpm.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

}