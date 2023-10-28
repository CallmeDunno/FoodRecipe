package com.example.btl_cnpm.di

import com.example.btl_cnpm.data.repository.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
    fun provideLoginRepository(fAuth: FirebaseAuth, fFireStore: FirebaseFirestore) = LoginRepository(fAuth, fFireStore)

}