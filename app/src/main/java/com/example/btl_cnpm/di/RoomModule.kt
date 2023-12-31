package com.example.btl_cnpm.di

import android.content.Context
import androidx.room.Room
import com.example.btl_cnpm.data.local.BookmarkLocalDatabase
import com.example.btl_cnpm.data.local.RecipeLocal.RecipeLocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideBookmarkLocalDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context.applicationContext,
        BookmarkLocalDatabase::class.java,
        "Bookmark"
    ).build()

    @Singleton
    @Provides
    fun provideBookmarkLocalDao(database: BookmarkLocalDatabase) = database.bookmarkLocalDao()


    @Singleton
    @Provides
    fun provideRecipeLocalDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context.applicationContext,
        RecipeLocalDatabase::class.java,
        "RecipeLocal"
    ).build()

    @Singleton
    @Provides
    fun provideRecipeLocalDao(database: RecipeLocalDatabase) = database.recipeLocalDao()
}