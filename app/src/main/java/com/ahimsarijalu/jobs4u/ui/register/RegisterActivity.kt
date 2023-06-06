package com.ahimsarijalu.jobs4u.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.ahimsarijalu.jobs4u.R
import com.ahimsarijalu.jobs4u.data.repository.Result
import com.ahimsarijalu.jobs4u.databinding.ActivityRegisterBinding
import com.ahimsarijalu.jobs4u.ui.login.LoginActivity
import com.ahimsarijalu.jobs4u.utils.ViewModelFactory
import com.ahimsarijalu.jobs4u.utils.showProgressBar
import com.ahimsarijalu.jobs4u.utils.showSnackBar

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private var isValidForm: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupViewModel()

        binding.confirmPasswordTextField.editText?.doAfterTextChanged {
            validatePassword()
        }

        binding.passwordTextField.editText?.doAfterTextChanged {
            validatePassword()
        }

        binding.emailTextField.editText?.doAfterTextChanged {
            if (!Patterns.EMAIL_ADDRESS.matcher(binding.emailTextField.editText?.text.toString())
                    .matches()
            ) {
                binding.emailTextField.apply {
                    isErrorEnabled = true
                    error = getString(R.string.error_unvalid_email)
                }
                isValidForm = false
            } else {
                binding.emailTextField.apply {
                    isErrorEnabled = false
                    error = null
                }
                isValidForm = true
            }

        }

        binding.loginInRegister.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.registerBtn.setOnClickListener {
            if (isValidForm) {
                binding.apply {
                    binding.root.clearFocus()
                    viewModel.registerUser(
                        email = emailTextField.editText?.text.toString(),
                        password = passwordTextField.editText?.text.toString(),
                        name = nameTextField.editText?.text.toString()
                    ).observe(this@RegisterActivity) { result ->
                        when (result) {
                            is Result.Error -> {
                                showProgressBar(binding.progressBar, false)
                                showSnackBar(binding.root, result.error)
                            }

                            is Result.Loading -> showProgressBar(binding.progressBar, true)
                            is Result.Success -> {
                                showProgressBar(binding.progressBar, false)
                                Intent(this@RegisterActivity, LoginActivity::class.java).apply {
                                    putExtra(LoginActivity.booleanExtra, true)
                                    putExtra(LoginActivity.messageExtra, result.data)
                                    startActivity(this)
                                    finish()
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory())[RegisterViewModel::class.java]
    }

    private fun validatePassword() {
        binding.apply {
            if (confirmPasswordTextField.editText?.text.toString() != passwordTextField.editText?.text.toString()) {
                passwordTextField.error = getString(R.string.error_password_not_match)
                confirmPasswordTextField.error = getString(R.string.error_password_not_match)
                isValidForm = false
            } else {
                passwordTextField.apply {
                    error = null
                    isErrorEnabled = false
                }
                confirmPasswordTextField.apply {
                    error = null
                    isErrorEnabled = false
                }
                isValidForm = true
            }
        }
    }
}