package com.example.qrgenerator

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.qrgenerator.databinding.ActivityMainBinding
import com.example.qrgenerator.ui.activities.ScannerActivity
import com.example.qrgenerator.ui.fragments.GeneratorFragment
import com.example.qrgenerator.ui.fragments.ScannerFragment

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
        setCurrentFragment(generatorFragment)

        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.qrgenerator -> setCurrentFragment(generatorFragment)
                R.id.qrscanner -> startActivity(Intent(this, ScannerActivity::class.java))
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
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // feature requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            }
        requestPermissionLauncher.launch(
            Manifest.permission.CAMERA)



    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    }
}