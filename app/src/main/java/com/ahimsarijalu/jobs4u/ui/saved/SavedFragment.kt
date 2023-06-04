package com.ahimsarijalu.jobs4u.ui.saved

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ahimsarijalu.jobs4u.R
import com.ahimsarijalu.jobs4u.databinding.FragmentSavedBinding
import com.ahimsarijalu.jobs4u.ui.login.LoginActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SavedFragment : Fragment(){

    private lateinit var viewModel: SavedViewModel


    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentSavedBinding.inflate(inflater, container, false)

        val isLogin = true
        if (!isLogin) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Access denied")
                .setMessage("You need to log in to view this content")
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                    findNavController().navigate(R.id.nav_home)
                }
                .setPositiveButton("Login") { _, _ ->
                    Intent(activity, LoginActivity::class.java).apply {
                        startActivity(this)
                        findNavController().navigate(R.id.nav_home)
                    }
                }
                .show()


        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[SavedViewModel::class.java]
    }


}