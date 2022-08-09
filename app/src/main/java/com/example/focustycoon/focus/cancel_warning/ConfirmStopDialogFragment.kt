package com.example.focustycoon.focus.cancel_warning

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.focustycoon.R
import com.example.focustycoon.databinding.DialogConfirmStopBinding

class ConfirmStopDialogFragment: DialogFragment() {

    companion object {
        const val KEY = "ConfirmStopDialogFragment"
        const val CONFIRM_KEY = "confirm"
    }

    private lateinit var binding: DialogConfirmStopBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_confirm_stop, container, false)
        binding.dialog = this
        binding.lifecycleOwner = viewLifecycleOwner
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    fun onConfirmButtonPressed() {
        setFragmentResult(KEY, bundleOf(CONFIRM_KEY to true))
        findNavController().navigateUp()
    }

    fun onCancelButtonPressed() {
        setFragmentResult(KEY, bundleOf(CONFIRM_KEY to false))
        findNavController().navigateUp()
    }
}