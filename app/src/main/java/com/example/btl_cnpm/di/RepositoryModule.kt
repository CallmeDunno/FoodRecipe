package com.example.btl_cnpm.di

import com.example.btl_cnpm.data.local.BookmarkLocalDao
import com.example.btl_cnpm.data.local.BookmarkLocalRepository
import com.example.btl_cnpm.data.repository.HomeRepository
import com.example.btl_cnpm.data.repository.LoginRepository
import com.example.btl_cnpm.data.repository.NewRecipeRepository
import com.example.btl_cnpm.data.repository.RecipeRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideLoginRepository(fAuth: FirebaseAuth, fFireStore: FirebaseFirestore) =
        LoginRepository(fAuth, fFireStore)

    @Singleton
    @Provides
    fun provideHomeRepository(fAuth: FirebaseAuth, fFireStore: FirebaseFirestore) =
        HomeRepository(fAuth, fFireStore)

    @Singleton
    @Provides
    fun provideNewRepository(
        fAuth: FirebaseAuth,
        fFireStore: FirebaseFirestore,
        fStorage: FirebaseStorage
    ) =
        NewRecipeRepository(fAuth, fFireStore, fStorage)

    @Singleton
    @Provides
    fun provideBookmarkLocalRepository(bookmarkDao: BookmarkLocalDao) =
        BookmarkLocalRepository(bookmarkDao)

    @Singleton
    @Provides
    fun provideRecipeRepository(fFireStore: FirebaseFirestore) = RecipeRepository(fFireStore)
}