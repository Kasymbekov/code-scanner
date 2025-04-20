package com.example.qrgenerator.ui.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.qrgenerator.R
import com.example.qrgenerator.databinding.ActivityMainBinding
import com.example.qrgenerator.ui.scanner.ScannerActivity
import com.example.qrgenerator.ui.generator.GeneratorFragment
import com.example.qrgenerator.ui.history.HistoryFragment
import com.example.qrgenerator.ui.scanner.ScannerFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val navController by lazy {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_host_main) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.scan -> startActivity(Intent(this, ScannerActivity::class.java))
                R.id.generate -> navController.navigate(R.id.generatorFragment)
                R.id.history -> navController.navigate(R.id.historyFragment)
            }
            true
        }

        // request permission for camera
        val requestPermissionLauncher =
            this.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    Toast.makeText(this, "camera permission granted", Toast.LENGTH_SHORT).show()
                }
            }
        requestPermissionLauncher.launch(
            Manifest.permission.CAMERA
        )
    }
}