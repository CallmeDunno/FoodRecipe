package com.example.btl_cnpm.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookmarkLocalDao {
    @Query("SELECT * FROM Bookmark")
    fun getAllItemBookmark() : LiveData<List<BookmarkLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItemBookmark(bookmarkLocal: BookmarkLocal)

    @Query("DELETE FROM Bookmark WHERE id = :id")
    fun deleteItemBookmark(id : Int)
}