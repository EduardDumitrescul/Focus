package com.example.focustycoon.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.focustycoon.MainApplication
import com.example.focustycoon.R
import com.example.focustycoon.databinding.ActivitySettingsBinding
import javax.inject.Inject


class SettingsActivity: AppCompatActivity() {
    lateinit var binding: ActivitySettingsBinding

    @Inject lateinit var globalSettings: GlobalSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MainApplication).appComponent.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)
        binding.activity = this
        binding.soundSwitch.isChecked = globalSettings.soundEnabled
        binding.soundSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            globalSettings.soundEnabled = isChecked
        }
        binding.notificationSwitch.isChecked = globalSettings.notificationsEnabled
        binding.notificationSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            globalSettings.notificationsEnabled = isChecked

        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

    }

    fun openPrivacyPolicy() {
        val browserIntent = Intent(Intent.ACTION_VIEW)
        browserIntent.data = Uri.parse("https://github.com/EduardDumitrescul/Focus-public/blob/main/privacy_policy.md")
        try {
            startActivity(browserIntent)
        }
        catch(exception: Exception) {

        }
    }

    fun openTermsAndConditions() {
        val browserIntent = Intent(Intent.ACTION_VIEW)
        browserIntent.data = Uri.parse("https://github.com/EduardDumitrescul/Focus-public/blob/main/terms_and_conditions.md")
        try {
            startActivity(browserIntent)
        }
        catch(exception: Exception) {

        }
    }
}