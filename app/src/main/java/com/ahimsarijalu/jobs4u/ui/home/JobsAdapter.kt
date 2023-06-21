package com.ahimsarijalu.jobs4u.ui.home

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.ahimsarijalu.jobs4u.R
import com.ahimsarijalu.jobs4u.data.datasource.local.model.Job
import com.ahimsarijalu.jobs4u.databinding.JobsLayoutBinding
import com.bumptech.glide.Glide

class JobsAdapter(private val activity: Activity, private var dataSet: List<Job>) :
    RecyclerView.Adapter<JobsAdapter.ListViewHolder>() {

    private lateinit var onItemCheckedCallback: OnItemCheckedCallback

    fun setOnItemCheckedCallback(onItemCheckedCallback: OnItemCheckedCallback) {
        this.onItemCheckedCallback = onItemCheckedCallback
    }

    inner class ListViewHolder(var binding: JobsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val circularProgressDrawable = CircularProgressDrawable(activity).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }

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
            Glide.with(holder.itemView)
                .load(item.avatarUrl)
                .circleCrop()
                .placeholder(circularProgressDrawable)
                .into(avatarShare)
            tvShareName.text = item.name
            tvShareUsername.text = item.username
            tvShareContent.text = item.text
            saveBtn.isChecked = item.saved as Boolean
            item.allImage?.let { showImage(this, it) }

            saveBtn.setOnCheckedChangeListener { _, isChecked ->
                saveBtn.isChecked = isChecked
                onItemCheckedCallback.onItemChecked(holder, item, isChecked)
            }
        }

    }

    override fun getItemCount() = dataSet.size

    private fun bindImageAndApplyOnClick(
        imageView: ImageView,
        imageUrls: List<String>,
        position: Int,
    ) {
        imageView.setOnClickListener {
            goToDetail(ArrayList(imageUrls), position, imageView)
        }

        imageView.transitionName =
            imageView.context.getString(R.string.shared_image_transition, position)



        Glide.with(imageView.context)
            .load(imageUrls[position])
            .optionalCenterCrop()
            .placeholder(circularProgressDrawable)
            .into(imageView)
    }

    private fun showImage(binding: JobsLayoutBinding, imageUrls: List<String>) {
        when (imageUrls.size) {
            1 -> {
                binding.imageView2.visibility = View.GONE
                binding.imageView3.visibility = View.GONE
                binding.imageView4.visibility = View.GONE

                bindImageAndApplyOnClick(binding.imageView, imageUrls, 0)
            }

            2 -> {
                binding.imageView3.visibility = View.GONE
                binding.imageView4.visibility = View.GONE

                bindImageAndApplyOnClick(binding.imageView, imageUrls, 0)
                bindImageAndApplyOnClick(binding.imageView2, imageUrls, 1)
            }

            3 -> {
                binding.imageView4.visibility = View.GONE

                bindImageAndApplyOnClick(binding.imageView, imageUrls, 0)
                bindImageAndApplyOnClick(binding.imageView2, imageUrls, 1)
                bindImageAndApplyOnClick(binding.imageView3, imageUrls, 2)
            }

            4 -> {
                bindImageAndApplyOnClick(binding.imageView, imageUrls, 0)
                bindImageAndApplyOnClick(binding.imageView2, imageUrls, 1)
                bindImageAndApplyOnClick(binding.imageView3, imageUrls, 2)
                bindImageAndApplyOnClick(binding.imageView4, imageUrls, 3)
            }

            else -> {
                binding.imageView.visibility = View.GONE
                binding.imageView2.visibility = View.GONE
                binding.imageView3.visibility = View.GONE
                binding.imageView4.visibility = View.GONE
            }
        }
    }

    private fun goToDetail(urls: ArrayList<String>, position: Int, sharedElement: ImageView) {
        activity.startActivity(
            ImageOpenActivity.createIntent(activity.applicationContext, urls, position),
            getActivityOption(sharedElement).toBundle()
        )
    }


    private fun getActivityOption(targetView: View): ActivityOptionsCompat {
        return ActivityOptionsCompat.makeSceneTransitionAnimation(
            activity,
            targetView,
            targetView.transitionName
        )
    }

    fun removeItem(jobData: Job) {
        val index = dataSet.indexOf(jobData)
        if (index != -1) {
            dataSet = dataSet.toMutableList().apply { removeAt(index) }
            notifyItemRemoved(index)
        }
    }

    interface OnItemCheckedCallback {
        fun onItemChecked(viewHolder: ListViewHolder, jobData: Job, isChecked: Boolean)
    }

}