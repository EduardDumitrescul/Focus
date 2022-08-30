package com.example.focustycoon.dagger

import android.app.Application
import com.example.focustycoon.focus.FocusFragment
import com.example.focustycoon.MainActivity
import com.example.focustycoon.focus.upgrade.UpgradeDialogFragment
import com.example.focustycoon.settings.PolicyConfirmDialog
import com.example.focustycoon.settings.SettingsActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
    fun inject(activity: MainActivity)
    fun inject(fragment: FocusFragment)
    fun inject(dialogFragment: UpgradeDialogFragment)
    fun inject(activity: SettingsActivity)
    fun inject(dialogFragment: PolicyConfirmDialog) {

    }
}