package com.example.btl_cnpm.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookmarkLocalDao {
    @Query("SELECT * FROM Bookmark")
    fun getAllNotes() : LiveData<List<BookmarkLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(bookmarkLocal: BookmarkLocal)

    @Query("DELETE FROM Bookmark WHERE id = :id")
    fun deleteNote(id : Int)
}