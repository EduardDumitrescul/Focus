package com.example.focus.dagger

import android.app.Application
import com.example.focus.focus.FocusFragment
import com.example.focus.MainActivity
import com.example.focus.focus.upgrade.UpgradeDialogFragment
import com.example.focus.settings.PolicyConfirmDialog
import com.example.focus.settings.SettingsActivity
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