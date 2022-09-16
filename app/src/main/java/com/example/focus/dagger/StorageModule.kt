@file:Suppress("unused")

package com.example.focus.dagger

import android.content.SharedPreferences
import com.example.focus.settings.GlobalSettings
import com.example.focus.storage.UserDataSource
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

    @Provides
    @Singleton
    fun provideGlobalSetting(sharedPreferences: SharedPreferences): GlobalSettings {
        return GlobalSettings(sharedPreferences)
    }
}