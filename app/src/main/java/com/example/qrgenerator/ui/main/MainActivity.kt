package com.example.qrgenerator.ui.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.qrgenerator.R
import com.example.qrgenerator.databinding.ActivityMainBinding
import com.example.qrgenerator.ui.scanner.ScannerActivity
import com.example.qrgenerator.ui.generator.GeneratorFragment
import com.example.qrgenerator.ui.history.HistoryFragment
import com.example.qrgenerator.ui.scanner.ScannerFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val generatorFragment = GeneratorFragment()
        val scannerFragment = ScannerFragment()
        val historyFragment = HistoryFragment()
        setCurrentFragment(generatorFragment)

        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.scan -> startActivity(Intent(this, ScannerActivity::class.java))
                R.id.generate -> setCurrentFragment(generatorFragment)
                R.id.history -> setCurrentFragment(historyFragment)
            }
            true
        }

        Toast.makeText(this, this.javaClass.name, Toast.LENGTH_LONG).show()

        // request permission for camera
        val requestPermissionLauncher =
            this.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show()
                }
            }
        requestPermissionLauncher.launch(
            Manifest.permission.CAMERA
        )
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    }
}