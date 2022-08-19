package com.example.focustycoon.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.text.BoringLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.focustycoon.R
import com.example.focustycoon.databinding.ActivitySettingsBinding


class SettingsActivity: AppCompatActivity() {
    lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)
        binding.activity = this
    }


    fun openPrivacyPolicy() {
        val browserIntent = Intent(Intent.ACTION_VIEW)
        browserIntent.data = Uri.parse("https://github.com/EduardDumitrescul/Focus-public/blob/main/privacy_policy.md")
        startActivity(browserIntent)
    }

    fun openTermsAndConditions() {
        val browserIntent = Intent(Intent.ACTION_VIEW)
        browserIntent.data = Uri.parse("https://github.com/EduardDumitrescul/Focus-public/blob/main/terms_and_conditions.md")
        startActivity(browserIntent)
    }
}