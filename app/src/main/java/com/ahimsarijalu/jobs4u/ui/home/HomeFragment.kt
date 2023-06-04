package com.ahimsarijalu.jobs4u.ui.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahimsarijalu.jobs4u.data.datasource.local.model.Jobs
import com.ahimsarijalu.jobs4u.databinding.FragmentHomeBinding

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


    private fun setupFilter() {
        binding.apply {
            val filters = listOf("Location", "Job Type", "Experience Level", "Work Type")

            rvFilter.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter = FilterAdapter(filters)
            }

        }
    }

    private fun setupJobItems() {
        binding.apply {

            val jobs = listOf(
                Jobs(
                    "https://pbs.twimg.com/profile_images/1648905605460148224/aKIxgA9b_normal.jpg",
                    "Dadang Sudandang",
                    "@dadanguhuy",
                    "info loker di PT. Mencari Cinta Sejati hubungi dadangsudandang@company.com @hrdbacot #lokercot",
                    listOf()
                ),
                Jobs(
                    "https://pbs.twimg.com/profile_images/1648905605460148224/aKIxgA9b_normal.jpg",
                    "Dadang Sudandang",
                    "@dadanguhuy",
                    "info loker di PT. Mencari Cinta Sejati hubungi dadangsudandang@company.com @hrdbacot #lokercot",
                    listOf(
                        "https://pbs.twimg.com/media/FxXuYk8aEAA6SBN?format=jpg&name=small"
                    )
                ),
                Jobs(
                    "https://pbs.twimg.com/profile_images/1648905605460148224/aKIxgA9b_normal.jpg",
                    "Dadang Sudandang",
                    "@dadanguhuy",
                    "info loker di PT. Mencari Cinta Sejati hubungi dadangsudandang@company.com @hrdbacot #lokercot",
                    listOf(
                        "https://pbs.twimg.com/media/FxXuYk8aEAA6SBN?format=jpg&name=small",
                        "https://pbs.twimg.com/media/FxXuZBCacAADdMy?format=jpg&name=small",
                    )
                ),
                Jobs(
                    "https://pbs.twimg.com/profile_images/1648905605460148224/aKIxgA9b_normal.jpg",
                    "Dadang Sudandang",
                    "@dadanguhuy",
                    "info loker di PT. Mencari Cinta Sejati hubungi dadangsudandang@company.com @hrdbacot #lokercot",
                    listOf(
                        "https://pbs.twimg.com/media/FxXuYk8aEAA6SBN?format=jpg&name=small",
                        "https://pbs.twimg.com/media/FxXuZBCacAADdMy?format=jpg&name=small",
                        "https://pbs.twimg.com/media/FxXuZgeakAgUY5Y?format=jpg&name=small",
                    )
                ),
                Jobs(
                    "https://pbs.twimg.com/profile_images/1648905605460148224/aKIxgA9b_normal.jpg",
                    "Dadang Sudandang",
                    "@dadanguhuy",
                    "info loker di PT. Mencari Cinta Sejati hubungi dadangsudandang@company.com @hrdbacot #lokercot",
                    listOf(
                        "https://pbs.twimg.com/media/FxXuYk8aEAA6SBN?format=jpg&name=small",
                        "https://pbs.twimg.com/media/FxXuZBCacAADdMy?format=jpg&name=small",
                        "https://pbs.twimg.com/media/FxXuZgeakAgUY5Y?format=jpg&name=small",
                        "https://pbs.twimg.com/media/FxXZ4siaEAAU7r3?format=jpg&name=small"
                    )
                )
            )

            rvJobs.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = JobsAdapter(activity as Activity, jobs)
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}