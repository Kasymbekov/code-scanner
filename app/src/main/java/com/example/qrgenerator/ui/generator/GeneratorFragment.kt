package com.example.qrgenerator.ui.generator

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.qrgenerator.databinding.FragmentGeneratorBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class GeneratorFragment : Fragment() {
    private lateinit var binding: FragmentGeneratorBinding
    private lateinit var viewModel: GeneratorViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGeneratorBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[GeneratorViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGenerate.setOnClickListener {
            val input = binding.etInput.text.toString()
            val bitmap = viewModel.generateCode(input = input)
            binding.ivBarcode.setImageBitmap(bitmap)
            viewModel.saveImageToDownloadFolder("pdf1.png", bitmap, requireContext())
        }
    }



}