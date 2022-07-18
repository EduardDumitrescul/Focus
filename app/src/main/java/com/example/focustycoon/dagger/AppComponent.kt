package com.example.focustycoon.dagger

import android.app.Application
import com.example.focustycoon.focus.FocusFragment
import com.example.focustycoon.MainActivity
import dagger.BindsInstance
import dagger.Component

@Component
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
    fun inject(activity: MainActivity)
    fun inject(fragment: FocusFragment)
}