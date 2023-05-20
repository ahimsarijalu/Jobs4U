package com.ahimsarijalu.jobs4u.ui.saved

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ahimsarijalu.jobs4u.R
import com.ahimsarijalu.jobs4u.ui.home.HomeFragment
import com.ahimsarijalu.jobs4u.ui.login.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SavedFragment : Fragment(), NavController.OnDestinationChangedListener {


    private lateinit var viewModel: SavedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val isLogin = false
        if (!isLogin) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Access denied")
                .setMessage("You need to log in to view this content")
                .setNegativeButton("Cancel") { dialog, which ->
                    // Respond to negative button press
                    dialog.dismiss()
                    activity?.findNavController(R.id.bottom_navigation)?.navigate(R.id.nav_home)
                }
                .setPositiveButton("Login") { dialog, which ->
                    Intent(activity, LoginActivity::class.java).apply {
                        startActivity(this)
                        activity?.finish()
                    }
                }
                .show()


        }
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SavedViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?,
    ) {

    }

}