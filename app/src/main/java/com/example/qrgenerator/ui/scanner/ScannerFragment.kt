package com.example.qrgenerator.ui.scanner

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.qrgenerator.databinding.FragmentScannerBinding
import com.example.qrgenerator.ui.scanner.ScannerViewModel
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScannerFragment : Fragment(), ZXingScannerView.ResultHandler {
    private var _binding: FragmentScannerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ScannerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.startCamera(
            requireContext(),
            binding.previewView.surfaceProvider,
            viewLifecycleOwner
        ) // нужен рефакторинг
    }

    override fun handleResult(result: Result?) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        Log.v("MyLog", result!!.text)
        Log.v("MyLog", result.barcodeFormat.toString())
        val clip: ClipData = ClipData.newPlainText("text", result.text)
        clipboard.setPrimaryClip(clip)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}