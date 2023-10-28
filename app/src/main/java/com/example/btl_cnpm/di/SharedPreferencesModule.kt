package com.example.btl_cnpm.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.btl_cnpm.utils.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class SharedPreferencesModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application) =
        application.applicationContext.getSharedPreferences(
            "data_user",
            Context.MODE_PRIVATE
        )

    @Provides
    @Singleton
    fun provideSharePreferencesManager(sharedPreferences: SharedPreferences) =
        SharedPreferencesManager(sharedPreferences)
}