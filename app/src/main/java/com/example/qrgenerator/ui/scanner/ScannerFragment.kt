package com.example.qrgenerator.ui.scanner

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.qrgenerator.databinding.FragmentScannerBinding

class ScannerFragment : Fragment() {
    private var _binding: FragmentScannerBinding? = null
    private val binding get() = _binding!! // safe call
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
        launchCamera()
        viewModel.scannedValue.observe(viewLifecycleOwner) { scannedText ->
            scannedText?.let {
                showResult(scannedText)
            }
        }
    }

    private fun launchCamera() {
        viewModel.startCamera(
            requireContext(),
            binding.previewView.surfaceProvider,
            viewLifecycleOwner
        )
    }

    private fun showResult(scannedText: String) {
        if (scannedText.isNotEmpty()) {
            viewModel.pause(requireContext())
            val show = AlertDialog.Builder(requireContext())
                .setTitle("Result")
                .setMessage(scannedText)
                .setPositiveButton("Copy to clipboard") { dialog, which ->
                    viewModel.copyToClipboard(requireContext(), scannedText)
                    finish()
                }
                .setNegativeButton("Scan again") { dialog, _ ->
                    dialog.dismiss()
                    launchCamera()
                }
                .setNeutralButton("Finish") { _, _ ->
                    finish()
                }
                .setOnKeyListener { _, code, event ->
                    if (code == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                        launchCamera()
                    }
                    false
                }
                .show()
        }
    }

    private fun finish() {
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}