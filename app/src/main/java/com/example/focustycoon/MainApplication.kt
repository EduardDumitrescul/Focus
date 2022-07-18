package com.example.focustycoon

import android.app.Application
import com.example.focustycoon.dagger.AppComponent
import com.example.focustycoon.dagger.DaggerAppComponent

class MainApplication: Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
}