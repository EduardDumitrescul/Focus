package com.example.focustycoon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.focustycoon.databinding.FragmentFocusBinding

class FocusFragment: Fragment() {
    private lateinit var binding: FragmentFocusBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_focus, container, false)
        return binding.root
    }
}