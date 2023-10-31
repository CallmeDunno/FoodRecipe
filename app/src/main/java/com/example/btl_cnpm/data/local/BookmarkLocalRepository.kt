package com.example.btl_cnpm.data.local

import javax.inject.Inject

class BookmarkLocalRepository @Inject constructor(private val dao: BookmarkLocalDao) {

    fun getAllItemBookmark() = dao.getAllItemBookmark()

    fun insertItem(bookmarkLocal: BookmarkLocal) = dao.insertItemBookmark(bookmarkLocal)

    fun deleteItem(id: Int) = dao.deleteItemBookmark(id)

}