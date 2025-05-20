package com.example.qrgenerator.ui.splashscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.qrgenerator.R
import com.example.qrgenerator.databinding.FragmentSplashscreenBinding
import com.example.qrgenerator.ui.splashscreen.SplashScreenViewModel

class SplashScreenFragment: Fragment(R.layout.fragment_splashscreen){
    private var _binding: FragmentSplashscreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SplashScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashscreenBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity())[SplashScreenViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}