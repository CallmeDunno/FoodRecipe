package com.example.btl_cnpm.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookmarkLocalDao {
    @Query("SELECT * FROM Bookmark WHERE idUser = :idUser")
    fun getAllItemBookmark(idUser: String) : LiveData<List<BookmarkLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItemBookmark(bookmarkLocal: BookmarkLocal)

    @Delete
    fun deleteItemBookmark(bookmarkLocal: BookmarkLocal)

    @Query("SELECT * FROM Bookmark WHERE idRecipe = :idRecipe AND idUser = :idUser")
    fun isExistsItem(idRecipe: String, idUser: String): List<BookmarkLocal>
}