package com.example.qrgenerator.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.qrgenerator.R
import com.example.qrgenerator.databinding.FragmentHistoryBinding
import com.example.qrgenerator.databinding.FragmentSplashscreenBinding
import com.example.qrgenerator.ui.splashscreen.SplashScreenViewModel

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var viewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity())[HistoryViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        viewModel.liveData.observe(this, Observer {
            binding.tv.text = it
        })
    }
}