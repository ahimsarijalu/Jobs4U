package com.ahimsarijalu.jobs4u.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahimsarijalu.jobs4u.R
import com.ahimsarijalu.jobs4u.data.datasource.local.model.Jobs
import com.ahimsarijalu.jobs4u.databinding.JobsLayoutBinding
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout

class JobsAdapter (private val dataSet: List<Jobs>) :
    RecyclerView.Adapter<JobsAdapter.ListViewHolder>() {
    class ListViewHolder(var binding: JobsLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = JobsLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = dataSet[position]

        holder.binding.apply {
            tvShareName.text = item.name
            tvShareUsername.text = item.username
            tvShareContent.text = item.content
        }
    }

    override fun getItemCount() = dataSet.size

}