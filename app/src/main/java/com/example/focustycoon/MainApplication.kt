package com.example.focustycoon

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.example.focustycoon.dagger.AppComponent
import com.example.focustycoon.dagger.DaggerAppComponent

private const val TAG = "MainApplication"

class MainApplication: Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(object: ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {
                Log.d(TAG, "onActivityDestroyed()")
                if(activity is MainActivity)
                    activity.saveData()
            }

        })
    }
}