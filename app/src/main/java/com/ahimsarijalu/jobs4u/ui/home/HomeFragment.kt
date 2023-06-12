package com.ahimsarijalu.jobs4u.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.ahimsarijalu.jobs4u.databinding.FragmentHomeBinding
import com.ahimsarijalu.jobs4u.ui.login.LoginActivity
import com.ahimsarijalu.jobs4u.ui.saved.SavedViewModel
import com.ahimsarijalu.jobs4u.utils.ViewModelFactory
import com.ahimsarijalu.jobs4u.utils.showProgressBar
import com.ahimsarijalu.jobs4u.utils.showSnackBar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var location = ""
    private var jobType = ""
    private var expLevel = ""
    private var workType = ""

    private lateinit var filter: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        filter = getString(R.string.filters, location, jobType, expLevel, workType)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            searchView.editText
                .setOnEditorActionListener { v, actionId, event ->
                    searchBar.text = searchView.text
                    viewModel.search(getString(R.string.query, searchView.text, filter))
                        .observe(viewLifecycleOwner) { result ->
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
                                    setupJobItems(result.data)
                                }
                            }
                        }
                    searchView.hide()
                    false
                }
        }
        setupViewModel()
        setupFilter()

        viewModel.getAllJobs().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> showProgressBar(binding.progressBar, true)
                is Result.Error -> {
                    showProgressBar(binding.progressBar, false)
                    showSnackBar(binding.root, result.error)
                }

                is Result.Success -> {
                    showProgressBar(binding.progressBar, false)
                    setupJobItems(result.data)
                }
            }
        }

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory()
        )[HomeViewModel::class.java]
    }


    private fun setupFilter() {
        binding.apply {
            val filters = listOf("Location", "Job Type", "Experience Level", "Work Type")

            rvFilter.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                val rvAdapter = FilterAdapter(filters)
                adapter = rvAdapter

                rvAdapter.setOnItemClickCallback(object : FilterAdapter.OnItemClickCallback {
                    override fun onItemClicked(
                        data: Pair<String, String>,
                    ) {
                        when (data.first) {
                            "Location" -> {
                                location = data.second.substring(data.second.indexOf(' ') + 1)
                                filter = getString(
                                    R.string.filters,
                                    location,
                                    jobType,
                                    expLevel,
                                    workType
                                )
                            }

                            "Job Type" -> {
                                jobType = data.second
                                filter = getString(
                                    R.string.filters,
                                    location,
                                    if (jobType == expLevel) "" else jobType,
                                    expLevel,
                                    workType
                                )
                            }

                            "Experience Level" -> {
                                expLevel = data.second
                                filter = getString(
                                    R.string.filters,
                                    location,
                                    jobType,
                                    if (expLevel == jobType) "" else expLevel,
                                    workType
                                )
                            }

                            "Work Type" -> {
                                workType = data.second
                                filter = getString(
                                    R.string.filters,
                                    location,
                                    jobType,
                                    expLevel,
                                    workType
                                )
                            }
                        }

                        Log.d("DEBUG filter", filter)
                        viewModel.filter(getString(R.string.query, searchView.text, filter))
                            .observe(viewLifecycleOwner) { result ->
                                when (result) {
                                    is Result.Loading -> showProgressBar(binding.progressBar, true)
                                    is Result.Error -> {
                                        showProgressBar(binding.progressBar, false)
                                        showSnackBar(binding.root, result.error)
                                    }

                                    is Result.Success -> {
                                        showProgressBar(binding.progressBar, false)
                                        setupJobItems(result.data)
                                    }
                                }
                            }
                    }
                })


            }
        }
    }

    private fun setupJobItems(jobs: List<Job>) {
        binding.apply {
            rvJobs.apply {
                layoutManager = LinearLayoutManager(activity)
                val rvAdapter = JobsAdapter(
                    activity as Activity,
                    jobs
                )
                adapter = rvAdapter

                val savedViewModel = ViewModelProvider(
                    this@HomeFragment,
                    ViewModelFactory()
                )[SavedViewModel::class.java]

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
                                savedViewModel.saveJob(jobData)
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
                            savedViewModel.removeSavedJob(jobData)
                                .observe(viewLifecycleOwner) { result ->
                                    processResult(result)
                                }
                        }
                    }

                })
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}