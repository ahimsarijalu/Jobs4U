package com.ahimsarijalu.jobs4u.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahimsarijalu.jobs4u.R
import com.ahimsarijalu.jobs4u.data.datasource.local.model.Jobs
import com.ahimsarijalu.jobs4u.databinding.FragmentHomeBinding
import com.ahimsarijalu.jobs4u.ui.login.LoginActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            searchView.editText
                .setOnEditorActionListener { v, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    false
                }
        }
        setupFilter()
        setupJobItems()

    }

    private fun setupJobItems() {
        binding.apply {

            val jobs = listOf(
                Jobs(
                    "https://avatar_url",
                    "Dadang Sudandang",
                    "@dadanguhuy",
                    "info loker di PT. Mencari Cinta Sejati hubungi dadangsudandang@company.com @hrdbacot #lokercot"
                ),
                Jobs(
                    "https://avatar_url",
                    "Dadang Sudandang",
                    "@dadanguhuy",
                    "info loker di PT. Mencari Cinta Sejati hubungi dadangsudandang@company.com @hrdbacot #lokercot"
                ),
                Jobs(
                    "https://avatar_url",
                    "Dadang Sudandang",
                    "@dadanguhuy",
                    "info loker di PT. Mencari Cinta Sejati hubungi dadangsudandang@company.com @hrdbacot #lokercot"
                )
            )

            rvJobs.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = JobsAdapter(jobs)
            }

        }
    }

    private fun setupFilter() {
        binding.apply {
            val filters = listOf("Job Type", "Experience Level", "Work Type")

            rvFilter.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter = FilterAdapter(filters)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}