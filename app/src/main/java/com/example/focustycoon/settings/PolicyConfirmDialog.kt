package com.example.focustycoon.settings

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.focustycoon.MainApplication
import com.example.focustycoon.R
import com.example.focustycoon.databinding.DialogPolicyConfirmBinding
import javax.inject.Inject

class PolicyConfirmDialog: DialogFragment() {
    @Inject
    lateinit var globalSettings: GlobalSettings
    lateinit var binding: DialogPolicyConfirmBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MainApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_policy_confirm, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.dialog = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
    }

    fun accept() {
        globalSettings.policyConfirmed = true
        findNavController().navigateUp()
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