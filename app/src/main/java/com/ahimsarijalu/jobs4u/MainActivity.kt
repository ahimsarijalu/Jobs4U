package com.ahimsarijalu.jobs4u

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ahimsarijalu.jobs4u.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}