package com.ahimsarijalu.jobs4u.utils

import android.view.View
import android.widget.ProgressBar
import com.google.android.material.snackbar.Snackbar

fun showSnackBar(context: View, message: String) {
    Snackbar.make(context, message, Snackbar.LENGTH_SHORT).show()
}

fun showProgressBar(progressBar: ProgressBar, isLoading: Boolean) {
    progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
}