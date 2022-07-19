package com.example.focustycoon.dagger

import android.content.SharedPreferences
import com.example.focustycoon.storage.UserDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {
    @Provides
    @Singleton
    fun provideUserDataSource(sharedPreferences: SharedPreferences): UserDataSource {
        return UserDataSource(sharedPreferences)
    }
}