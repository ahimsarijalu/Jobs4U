package com.ahimsarijalu.jobs4u

import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ahimsarijalu.jobs4u.databinding.ActivityMainBinding
import com.ahimsarijalu.jobs4u.ui.home.HomeFragment
import com.ahimsarijalu.jobs4u.ui.login.LoginActivity
import com.ahimsarijalu.jobs4u.ui.profile.ProfileFragment
import com.ahimsarijalu.jobs4u.ui.saved.SavedFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        replaceFragment(HomeFragment())

        Intent(this, LoginActivity::class.java).let {
            startActivity(it)
        }

//        val navController = findNavController(R.id.fragment_container)
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.nav_home,
//                R.id.nav_saved,
//                R.id.nav_profile
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        binding.bottomNavigation.setupWithNavController(navController)

//        binding.bottomNavigation.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.menu_home -> {
//                    replaceFragment(HomeFragment())
//                    true
//                }
//
//                R.id.menu_saved -> {
//                    replaceFragment(SavedFragment())
//                    true
//                }
//
//                R.id.menu_profile -> {
//                    replaceFragment(ProfileFragment())
//                    true
//                }
//
//                else -> false
//            }
//        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
    }
}