package com.example.btl_cnpm.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btl_cnpm.data.local.BookmarkLocal
import com.example.btl_cnpm.data.local.BookmarkLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(private val repository: BookmarkLocalRepository) :
    ViewModel() {

    private lateinit var listItemBookmark: LiveData<List<BookmarkLocal>>

    fun getAllItemBookmark(id: String): LiveData<List<BookmarkLocal>> {
        viewModelScope.launch {
            val def = async {
                return@async repository.getAllItemBookmark(id)
            }
            listItemBookmark = def.await()
        }
        return listItemBookmark
    }

    fun insertBookmark(itemBookmarkLocal: BookmarkLocal) {
        viewModelScope.launch {
            repository.insertItem(itemBookmarkLocal)
        }
    }

    fun deleteItemBookmark(id: Int) {
        viewModelScope.launch {
            repository.deleteItem(id)
        }
    }
}