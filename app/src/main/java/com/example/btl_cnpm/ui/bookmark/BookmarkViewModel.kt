package com.example.btl_cnpm.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btl_cnpm.data.local.BookmarkLocal
import com.example.btl_cnpm.data.local.BookmarkLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(private val repository: BookmarkLocalRepository) :
    ViewModel() {

    fun getAllItemBookmark(id: String) = repository.getAllItemBookmark(id)

    fun insertBookmark(itemBookmarkLocal: BookmarkLocal) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertItem(itemBookmarkLocal)
        }
    }

    fun deleteItemBookmark(bookmarkLocal: BookmarkLocal) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(bookmarkLocal)
        }
    }
}