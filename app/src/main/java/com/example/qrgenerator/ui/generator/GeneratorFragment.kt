package com.example.qrgenerator.ui.generator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.qrgenerator.R
import com.example.qrgenerator.databinding.FragmentGeneratorBinding

class GeneratorFragment : Fragment() {
    private lateinit var binding: FragmentGeneratorBinding
    private lateinit var viewModel: GeneratorViewModel

    init {
        Log.e("AMG", "GeneratorFragment created()")
    }

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
            val editText = binding.etInput.text
            val bitmap = viewModel.generateCode(input = editText.toString())
            binding.ivBarcode.setImageBitmap(bitmap)
            viewModel.saveImageToDownloadFolder(bitmap, requireContext())
            editText.clear()
        }

//        view.postDelayed(
//            {
//                findNavController().navigate(
//                    R.id.action_generatorFragment_to_historyFragment
//                )
//            }, 2000
//        )
    }


}