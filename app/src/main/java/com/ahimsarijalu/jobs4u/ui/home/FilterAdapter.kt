package com.ahimsarijalu.jobs4u.ui.home

import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahimsarijalu.jobs4u.R
import com.ahimsarijalu.jobs4u.databinding.FilterLayoutBinding
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout


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
                            setSimpleItems(
                                holder.itemView.resources.getStringArray(R.array.locations)
                                    .sortedBy { it.split(" ")[1] }.toTypedArray()
                            )
                            inputType = InputType.TYPE_CLASS_TEXT
                            minWidth = 560
                            setOnItemClickListener { parent, _, position, _ ->
                                onItemClickCallback.onItemClicked(
                                    Pair(
                                        "Location",
                                        parent.adapter.getItem(position).toString()
                                    )
                                )
                                showClearButton("Location")
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
                                showClearButton("Job Type")
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
                                showClearButton("Experience Level")
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
                                showClearButton("Work Type")
                            }
                        }
                    }
                }
            }
            filter.editText?.hint = dataSet[position]
        }

    }

    private fun MaterialAutoCompleteTextView.showClearButton(filterType: String) {
        val textInputLayout = parent.parent as? TextInputLayout
        textInputLayout?.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
        textInputLayout?.setEndIconOnClickListener {
            setText("")
            hideClearButton()
            onItemClickCallback.onItemClicked(Pair(filterType, ""))
        }
    }

    private fun MaterialAutoCompleteTextView.hideClearButton() {
        val textInputLayout = parent.parent as? TextInputLayout
        textInputLayout?.endIconMode = TextInputLayout.END_ICON_DROPDOWN_MENU
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Pair<String, String>)
    }


    override fun getItemCount() = dataSet.size

}