package com.example.btl_cnpm.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Bookmark")
data class BookmarkLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "IdRecipe")
    val idRecipe: String,
    @ColumnInfo(name = "IdUser")
    val idUser: String,
    @ColumnInfo(name = "NameRecipe")
    val name: String,
    @ColumnInfo(name = "Creator")
    val creator: String,
    @ColumnInfo(name = "Timer")
    val timer: Int,
    @ColumnInfo(name = "Rate")
    val rate: Float,
    @ColumnInfo(name = "Ingredient")
    val ingredient: String,
    @ColumnInfo(name = "Image")
    val image: String,
    @ColumnInfo(name = "Procedure")
    val procedure: String
)
