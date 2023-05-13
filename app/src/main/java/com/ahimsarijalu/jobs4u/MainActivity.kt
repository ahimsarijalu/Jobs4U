package com.ahimsarijalu.jobs4u

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ahimsarijalu.jobs4u.databinding.ActivityMainBinding
import com.ahimsarijalu.jobs4u.ui.home.HomeFragment
import com.ahimsarijalu.jobs4u.ui.profile.ProfileFragment
import com.ahimsarijalu.jobs4u.ui.saved.SavedFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.menu_saved -> {
                    replaceFragment(SavedFragment())
                    true
                }

                R.id.menu_profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
    }
}