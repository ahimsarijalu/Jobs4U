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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val user = Firebase.auth.currentUser

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
                    "https://pbs.twimg.com/profile_images/1571021380870864898/hDvWQyDZ_normal.jpg",
                    "Dadang Sudandang",
                    "@dadanguhuy",
                    "info loker di PT. Mencari Cinta Sejati hubungi dadangsudandang@company.com @hrdbacot #lokercot",
                    isSaved(listOf("FCb7xwmwXvUQBW4e2YyMxtnSFEm1", "hui2uehiu12e89188ehASAqwd")),
                    listOf()
                ),
                Jobs(
                    "https://pbs.twimg.com/profile_images/1571021380870864898/hDvWQyDZ_normal.jpg",
                    "Dadang Sudandang",
                    "@dadanguhuy",
                    "info loker di PT. Mencari Cinta Sejati hubungi dadangsudandang@company.com @hrdbacot #lokercot",
                    isSaved((listOf())),
                    listOf(
                        "https://pbs.twimg.com/media/FxXuYk8aEAA6SBN?format=jpg&name=small"
                    )
                ),
                Jobs(
                    "https://pbs.twimg.com/profile_images/1571021380870864898/hDvWQyDZ_normal.jpg",
                    "Dadang Sudandang",
                    "@dadanguhuy",
                    "info loker di PT. Mencari Cinta Sejati hubungi dadangsudandang@company.com @hrdbacot #lokercot",
                    isSaved(listOf()),
                    listOf(
                        "https://pbs.twimg.com/media/FxXuYk8aEAA6SBN?format=jpg&name=small",
                        "https://pbs.twimg.com/media/FxXuZBCacAADdMy?format=jpg&name=small",
                    )
                ),
                Jobs(
                    "https://pbs.twimg.com/profile_images/1571021380870864898/hDvWQyDZ_normal.jpg",
                    "Dadang Sudandang",
                    "@dadanguhuy",
                    "info loker di PT. Mencari Cinta Sejati hubungi dadangsudandang@company.com @hrdbacot #lokercot",
                    isSaved(listOf()),
                    listOf(
                        "https://pbs.twimg.com/media/FxXuYk8aEAA6SBN?format=jpg&name=small",
                        "https://pbs.twimg.com/media/FxXuZBCacAADdMy?format=jpg&name=small",
                        "https://pbs.twimg.com/media/FxXuZgeakAgUY5Y?format=jpg&name=small",
                    )
                ),
                Jobs(
                    "https://pbs.twimg.com/profile_images/1571021380870864898/hDvWQyDZ_normal.jpg",
                    "Dadang Sudandang",
                    "@dadanguhuy",
                    "info loker di PT. Mencari Cinta Sejati hubungi dadangsudandang@company.com @hrdbacot #lokercot",
                    isSaved(listOf()),
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

    private fun isSaved(listId: List<String>): Boolean {
        val found = listId.find { it == user?.uid }
        return found == user?.uid
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}