package com.example.qrgenerator.ui.scanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.qrgenerator.databinding.FragmentScannerBinding
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScannerFragment : Fragment() {
    private lateinit var binding: FragmentScannerBinding
    private lateinit var scannerView: ZXingScannerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        scannerView = ZXingScannerView(requireContext())
        binding = FragmentScannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}