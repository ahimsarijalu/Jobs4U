package com.ahimsarijalu.jobs4u.ui.saved

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahimsarijalu.jobs4u.R
import com.ahimsarijalu.jobs4u.data.datasource.local.model.Job
import com.ahimsarijalu.jobs4u.data.repository.Result
import com.ahimsarijalu.jobs4u.databinding.FragmentSavedBinding
import com.ahimsarijalu.jobs4u.ui.home.JobsAdapter
import com.ahimsarijalu.jobs4u.ui.login.LoginActivity
import com.ahimsarijalu.jobs4u.utils.ViewModelFactory
import com.ahimsarijalu.jobs4u.utils.showProgressBar
import com.ahimsarijalu.jobs4u.utils.showSnackBar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SavedFragment : Fragment() {

    private lateinit var viewModel: SavedViewModel


    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!

    private var user = Firebase.auth.currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentSavedBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this, ViewModelFactory())[SavedViewModel::class.java]

        val isLogin = user != null
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
            setupList()
        }
    }

    private fun setupList() {
        viewModel.getSavedJob().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showProgressBar(binding.progressBar, true)
                }

                is Result.Error -> {
                    showProgressBar(binding.progressBar, false)
                    showSnackBar(binding.root, result.error)
                }

                is Result.Success -> {
                    showProgressBar(binding.progressBar, false)
                    binding.rvSaved.apply {
                        layoutManager = LinearLayoutManager(activity)
                        val rvAdapter = JobsAdapter(activity as Activity, result.data)
                        adapter = rvAdapter

                        val user = Firebase.auth.currentUser

                        rvAdapter.setOnItemCheckCallback(object :
                            JobsAdapter.OnItemCheckedCallback {
                            override fun onItemChecked(
                                viewHolder: JobsAdapter.ListViewHolder,
                                jobData: Job,
                                isChecked: Boolean,
                            ) {
                                if (isChecked) {
                                    if (user != null) {
                                        viewModel.saveJob(jobData)
                                            .observe(viewLifecycleOwner) { result ->
                                                processResult(result)
                                            }
                                    } else {
                                        MaterialAlertDialogBuilder(requireContext())
                                            .setTitle("Access denied")
                                            .setMessage("You need to log in to save a job")
                                            .setNegativeButton("Cancel") { dialog, _ ->
                                                dialog.dismiss()
                                                findNavController().navigate(R.id.nav_home)
                                            }
                                            .setPositiveButton("Login") { _, _ ->
                                                Intent(
                                                    activity,
                                                    LoginActivity::class.java
                                                ).apply {
                                                    startActivity(this)
                                                    findNavController().navigate(R.id.nav_home)
                                                }
                                            }
                                            .show()
                                    }
                                } else {
                                    viewModel.removeSavedJob(jobData)
                                        .observe(viewLifecycleOwner) { result ->
                                            processResult(result)
                                        }
                                }
                            }

                        })
                    }
                }
            }
        }

    }

    private fun processResult(result: Result<String>) {
        when (result) {
            is Result.Loading -> showProgressBar(binding.progressBar, true)
            is Result.Error -> {
                showProgressBar(binding.progressBar, false)
                showSnackBar(binding.root, result.error)
            }

            is Result.Success -> {
                showProgressBar(binding.progressBar, false)
                showSnackBar(binding.root, result.data)
            }
        }
    }


}