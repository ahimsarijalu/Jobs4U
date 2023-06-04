package com.ahimsarijalu.jobs4u.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahimsarijalu.jobs4u.R
import com.ahimsarijalu.jobs4u.databinding.FilterLayoutBinding
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class FilterAdapter(private val dataSet: List<String>) :
    RecyclerView.Adapter<FilterAdapter.ListViewHolder>() {

    inner class ListViewHolder(var binding: FilterLayoutBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = FilterLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {


        holder.binding.filter.apply {
            hint = dataSet[position]
            (editText as? MaterialAutoCompleteTextView)?.setSimpleItems(R.array.work_types)
        }
        holder.binding.filter.editText?.hint = dataSet[position]
    }
    override fun getItemCount() = dataSet.size

}