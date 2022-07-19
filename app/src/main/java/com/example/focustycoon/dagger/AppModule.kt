package com.example.focustycoon.dagger

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.applicationContext.getSharedPreferences("app_data", Context.MODE_PRIVATE)
    }
}