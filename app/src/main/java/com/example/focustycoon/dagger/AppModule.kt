package com.example.focustycoon.dagger

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.focustycoon.SoundService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.applicationContext.getSharedPreferences("app_data", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSoundService(application: Application): SoundService {
        return SoundService(application.applicationContext)
    }
}