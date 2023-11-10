package com.example.btl_cnpm.di

import com.example.btl_cnpm.data.local.BookmarkLocalDao
import com.example.btl_cnpm.data.local.BookmarkLocalRepository
import com.example.btl_cnpm.data.repository.*
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
    fun provideHomeRepository(fFireStore: FirebaseFirestore) =
        HomeRepository(fFireStore)

    @Singleton
    @Provides
    fun provideSearchRepository(fFireStore: FirebaseFirestore) =
        SearchRepository(fFireStore)

    @Singleton
    @Provides
    fun provideProfileRepository(fFireStore: FirebaseFirestore,
                                 fStorage: FirebaseStorage
    ) = ProfileRepository(fFireStore, fStorage)

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
    fun provideRecipeRepository(fFireStore: FirebaseFirestore, bookmarkDao: BookmarkLocalDao) = RecipeRepository(fFireStore, bookmarkDao)
}