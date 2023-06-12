package com.ahimsarijalu.jobs4u.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ahimsarijalu.jobs4u.BuildConfig
import com.ahimsarijalu.jobs4u.R
import com.ahimsarijalu.jobs4u.data.repository.Result
import com.ahimsarijalu.jobs4u.databinding.FragmentProfileBinding
import com.ahimsarijalu.jobs4u.ui.changePassword.ChangePasswordActivity
import com.ahimsarijalu.jobs4u.ui.login.LoginActivity
import com.ahimsarijalu.jobs4u.utils.ViewModelFactory
import com.ahimsarijalu.jobs4u.utils.showProgressBar
import com.ahimsarijalu.jobs4u.utils.showSnackBar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val auth = Firebase.auth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelFactory())[ProfileViewModel::class.java]

        val isLogin = auth.currentUser != null
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
        } else {
            setupAction()
            setupView()
        }
    }

    override fun onResume() {
        if (message != null) showSnackBar(binding.root, message.toString())
        super.onResume()
    }

    private fun setupView() {
        viewModel.getUser().observe(viewLifecycleOwner) { result ->
            when(result) {
                is Result.Loading -> showProgressBar(binding.progressBar, true)
                is Result.Error -> {
                    showProgressBar(binding.progressBar, false)
                    showSnackBar(binding.root, result.error)
                }
                is Result.Success -> {
                    showProgressBar(binding.progressBar, false)
                    binding.tvProfileName.text = result.data.name
                    binding.tvProfileEmail.text = result.data.email
                }
            }
        }

        binding.tvAppVersion.text = getString(R.string.app_version, BuildConfig.VERSION_NAME)
    }

    private fun setupAction() {
        binding.logoutBtn.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("You want to logout?")
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton("Logout") { _, _ ->
                    viewModel.logout().observe(viewLifecycleOwner) { result ->
                        when (result) {
                            is Result.Loading -> showProgressBar(binding.progressBar, true)
                            is Result.Error -> {
                                showProgressBar(binding.progressBar, false)
                                showSnackBar(binding.root, result.error)
                            }

                            is Result.Success -> {
                                showSnackBar(binding.root, result.data)
                                findNavController().navigate(R.id.nav_home)
                            }
                        }
                    }
                }
                .show()
        }

        binding.changePasswordBtn.setOnClickListener {
            startActivity(Intent(context, ChangePasswordActivity::class.java))
        }
    }

    companion object {
        var message: String? = null
    }

}