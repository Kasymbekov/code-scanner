package com.example.qrgenerator.ui

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.qrgenerator.databinding.FragmentGeneratorBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder


class GeneratorFragment : Fragment() {
    private lateinit var binding: FragmentGeneratorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGeneratorBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGenerate.setOnClickListener {
            val multiFormatWriter = MultiFormatWriter()
            val bm = multiFormatWriter.encode(
                binding.etInput.text.toString(),
                BarcodeFormat.QR_CODE,
                300,
                300
            )
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bm)
            binding.ivBarcode.setImageBitmap(bitmap)
        }

    }

}