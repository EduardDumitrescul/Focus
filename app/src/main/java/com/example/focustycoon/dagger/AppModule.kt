package com.example.focustycoon.dagger

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.focustycoon.SoundService
import com.example.focustycoon.notification.AlarmManagerUtil
import com.example.focustycoon.settings.GlobalSettings
import com.example.focustycoon.storage.UserDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.applicationContext.getSharedPreferences("app_data", Context.MODE_PRIVATE)
    }

    private var globalSettings: GlobalSettings? = null

    @Provides
    @Singleton
    fun provideGlobalSetting(sharedPreferences: SharedPreferences): GlobalSettings {
        if(globalSettings == null)
            globalSettings = GlobalSettings(sharedPreferences)
        return globalSettings!!
    }

    @Provides
    @Singleton
    fun provideSoundService(application: Application, sharedPreferences: SharedPreferences): SoundService {
        if(globalSettings == null)
            globalSettings = GlobalSettings(sharedPreferences)
        return SoundService(application.applicationContext, globalSettings!!)
    }

    @Provides
    @Singleton
    fun provideAlarmManagerUtil(application: Application): AlarmManagerUtil {
        return AlarmManagerUtil(application.applicationContext)
    }

    @Provides
    @Singleton
    fun provideUserDataSource(sharedPreferences: SharedPreferences): UserDataSource {
        return UserDataSource(sharedPreferences)
    }
}