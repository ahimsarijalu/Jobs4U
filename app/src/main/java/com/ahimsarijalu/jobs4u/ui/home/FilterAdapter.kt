package com.ahimsarijalu.jobs4u.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.ahimsarijalu.jobs4u.R
import com.ahimsarijalu.jobs4u.databinding.FilterLayoutBinding
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout

class FilterAdapter(private val dataSet: List<String>) :
    RecyclerView.Adapter<FilterAdapter.ListViewHolder>() {

    class ListViewHolder(var binding: FilterLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding = FilterLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {


        holder.binding.filter.apply {
            hint = dataSet[position]
            (editText as? MaterialAutoCompleteTextView)?.setSimpleItems(R.array.work_types)
        }
//        viewHolder.filterIte =
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}