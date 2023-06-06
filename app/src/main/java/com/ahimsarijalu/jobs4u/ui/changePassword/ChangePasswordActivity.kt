package com.ahimsarijalu.jobs4u.ui.changePassword

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.ahimsarijalu.jobs4u.R
import com.ahimsarijalu.jobs4u.data.repository.Result
import com.ahimsarijalu.jobs4u.databinding.ActivityChangePasswordBinding
import com.ahimsarijalu.jobs4u.ui.profile.ProfileFragment.Companion.message
import com.ahimsarijalu.jobs4u.utils.ViewModelFactory
import com.ahimsarijalu.jobs4u.utils.showProgressBar
import com.ahimsarijalu.jobs4u.utils.showSnackBar
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var viewModel: ChangePasswordViewModel

    private lateinit var binding: ActivityChangePasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel = ViewModelProvider(this, ViewModelFactory())[ChangePasswordViewModel::class.java]

        binding.confirmPasswordTextField.editText?.doAfterTextChanged {
            validateNewPassword()
        }

        binding.newPasswordTextField.editText?.doAfterTextChanged {
            validateNewPassword()
        }

        binding.changePasswordSubmitBtn.setOnClickListener {
            when {
                binding.confirmPasswordTextField.editText?.text.toString().isEmpty() -> {
                    binding.confirmPasswordTextField.error =
                        getString(R.string.error_password_empty)
                }

                binding.newPasswordTextField.editText?.text.toString().isEmpty() -> {
                    binding.newPasswordTextField.error = getString(R.string.error_password_empty)
                }

                binding.oldPasswordTextField.editText?.text.toString().isEmpty() -> {
                    binding.oldPasswordTextField.error = getString(R.string.error_password_empty)
                }

                else -> {
                    MaterialAlertDialogBuilder(this).setTitle(getString(R.string.change_password_confirmation_message))
                        .setNegativeButton(getString(R.string.cancel)) { dialog, which ->
                            dialog.dismiss()
                        }
                        .setPositiveButton(getString(R.string.change_password_confirm)) { dialog, which ->
                            binding.root.clearFocus()
                            viewModel.changePassword(
                                binding.oldPasswordTextField.editText?.text.toString(),
                                binding.confirmPasswordTextField.editText?.text.toString()
                            )
                                .observe(this) { result ->
                                    when (result) {
                                        is Result.Loading -> showProgressBar(
                                            binding.progressBar,
                                            true
                                        )

                                        is Result.Error -> {
                                            showProgressBar(binding.progressBar, false)
                                            showSnackBar(binding.root, result.error)
                                        }

                                        is Result.Success -> {
                                            dialog.dismiss()
                                            showProgressBar(binding.progressBar, false)
                                            message = result.data
                                            finish()
                                        }
                                    }
                                }

                        }.show()
                }
            }
        }
    }

    private fun validateNewPassword() {
        binding.apply {
            if (confirmPasswordTextField.editText?.text.toString() != newPasswordTextField.editText?.text.toString()) {
                newPasswordTextField.error = getString(R.string.error_new_password_not_match)
                confirmPasswordTextField.error = getString(R.string.error_new_password_not_match)
            } else {
                oldPasswordTextField.apply {
                    isErrorEnabled = false
                    error = null
                }
                newPasswordTextField.apply {
                    isErrorEnabled = false
                    error = null
                }
                confirmPasswordTextField.apply {
                    isErrorEnabled = false
                    error = null
                }
            }
        }
    }
}