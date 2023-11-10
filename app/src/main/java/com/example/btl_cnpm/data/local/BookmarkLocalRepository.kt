package com.example.btl_cnpm.data.local

import javax.inject.Inject

class BookmarkLocalRepository @Inject constructor(private val dao: BookmarkLocalDao) {

    fun getAllItemBookmark(idUser: String) = dao.getAllItemBookmark(idUser)

    fun insertItem(bookmarkLocal: BookmarkLocal) = dao.insertItemBookmark(bookmarkLocal)

    fun deleteItem(bookmarkLocal: BookmarkLocal) = dao.deleteItemBookmark(bookmarkLocal)

}