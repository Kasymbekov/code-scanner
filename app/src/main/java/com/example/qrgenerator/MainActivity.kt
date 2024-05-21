package com.example.qrgenerator

import android.content.Intent
import android.os.Bundle
import android.provider.Telephony.Mms.Intents
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.qrgenerator.databinding.ActivityMainBinding
import com.example.qrgenerator.ui.GeneratorFragment
import com.example.qrgenerator.ui.ScannerFragment

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
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    }
}