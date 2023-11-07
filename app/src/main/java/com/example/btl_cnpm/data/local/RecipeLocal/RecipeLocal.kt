package com.example.btl_cnpm.data.local.RecipeLocal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RecipeLocal")
data class RecipeLocal (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "IdRecipe")
    val idRecipe: String,
    @ColumnInfo(name = "idCreator")
    val idCreator: String,
)