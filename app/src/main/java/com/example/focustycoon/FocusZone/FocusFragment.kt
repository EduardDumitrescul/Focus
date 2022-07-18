package com.example.focustycoon.FocusZone

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.focustycoon.MainActivity
import com.example.focustycoon.MainApplication
import com.example.focustycoon.R
import com.example.focustycoon.databinding.FragmentFocusBinding
import javax.inject.Inject

class FocusFragment: Fragment() {
    private lateinit var binding: FragmentFocusBinding

    @Inject
    lateinit var viewModel: FocusViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MainApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_focus, container, false)
        return binding.root
    }
}