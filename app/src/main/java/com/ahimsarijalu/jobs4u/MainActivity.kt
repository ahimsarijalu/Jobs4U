package com.ahimsarijalu.jobs4u

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ahimsarijalu.jobs4u.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        NavigationBarView.OnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.menu_home -> true
                R.id.menu_saved -> true
                R.id.menu_profile -> true
                else -> false
            }
        }

        binding.bottomNavigation.setOnItemReselectedListener { item ->
            when(item.itemId) {
                R.id.menu_home -> {}
                R.id.menu_saved -> {}
                R.id.menu_profile -> {}
            }
        }
    }
}