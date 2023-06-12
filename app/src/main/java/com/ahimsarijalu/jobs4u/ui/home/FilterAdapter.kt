package com.ahimsarijalu.jobs4u.ui.home

import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahimsarijalu.jobs4u.R
import com.ahimsarijalu.jobs4u.databinding.FilterLayoutBinding
import com.google.android.material.textfield.MaterialAutoCompleteTextView


class FilterAdapter(private val dataSet: List<String>) :
    RecyclerView.Adapter<FilterAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    inner class ListViewHolder(var binding: FilterLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = FilterLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {


        holder.binding.apply {
            filter.apply {
                hint = dataSet[position]
                when (dataSet[position]) {
                    "Location" -> {
                        (editText as? MaterialAutoCompleteTextView)?.apply {
                            setSimpleItems(R.array.locations)
                            inputType = InputType.TYPE_CLASS_TEXT
                            minWidth = 560
                            setOnItemClickListener { parent, view, position, id ->
                                onItemClickCallback.onItemClicked(
                                    Pair(
                                        "Location",
                                        parent.adapter.getItem(position).toString()
                                    )
                                )
                            }

                        }
                    }

                    "Job Type" -> {
                        (editText as? MaterialAutoCompleteTextView)?.apply {
                            setSimpleItems(R.array.job_types)
                            setOnItemClickListener { parent, _, position, _ ->
                                onItemClickCallback.onItemClicked(
                                    Pair(
                                        "Job Type",
                                        parent.adapter.getItem(position).toString()
                                    )
                                )
                            }
                        }
                    }

                    "Experience Level" -> {
                        (editText as? MaterialAutoCompleteTextView)?.apply {
                            setSimpleItems(R.array.experience_level)
                            setOnItemClickListener { parent, _, position, _ ->
                                onItemClickCallback.onItemClicked(
                                    Pair(
                                        "Experience Level",
                                        parent.adapter.getItem(position).toString()
                                    )
                                )
                            }
                        }
                    }

                    "Work Type" -> {
                        (editText as? MaterialAutoCompleteTextView)?.apply {
                            setSimpleItems(R.array.work_types)
                            setOnItemClickListener { parent, _, position, _ ->
                                onItemClickCallback.onItemClicked(
                                    Pair(
                                        "Work Type",
                                        parent.adapter.getItem(position).toString()
                                    )
                                )
                            }
                        }
                    }
                }
            }
            filter.editText?.hint = dataSet[position]
        }

    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Pair<String, String>)
    }


    override fun getItemCount() = dataSet.size

}