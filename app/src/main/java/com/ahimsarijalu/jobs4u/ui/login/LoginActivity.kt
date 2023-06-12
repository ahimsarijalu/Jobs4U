package com.ahimsarijalu.jobs4u.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ahimsarijalu.jobs4u.MainActivity
import com.ahimsarijalu.jobs4u.R
import com.ahimsarijalu.jobs4u.databinding.ActivityLoginBinding
import com.ahimsarijalu.jobs4u.databinding.ForgotPasswordBottomSheetBinding
import com.ahimsarijalu.jobs4u.ui.register.RegisterActivity
import com.ahimsarijalu.jobs4u.utils.ViewModelFactory
import com.ahimsarijalu.jobs4u.data.repository.Result
import com.ahimsarijalu.jobs4u.utils.showProgressBar
import com.ahimsarijalu.jobs4u.utils.showSnackBar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        val isRegistered = intent.getBooleanExtra(booleanExtra, false)
        val message = intent.getStringExtra(messageExtra)
        if (isRegistered) showSnackBar(binding.root, message.toString())

        setupViewModel()
        setupAction()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory()
        )[LoginViewModel::class.java]
    }

    private fun setupAction() {
        binding.apply {
            forgotPasswordBtn.setOnClickListener {
                val bottomSheet = ForgotPasswordBottomSheet()
                bottomSheet.show(supportFragmentManager, ForgotPasswordBottomSheet.TAG)
            }
            registerInLogin.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
            loginBtn.setOnClickListener {
                root.clearFocus()
                viewModel.login(
                    emailTextField.editText?.text.toString(),
                    passwordTextField.editText?.text.toString()
                ).observe(this@LoginActivity) { result ->
                    when (result) {
                        is Result.Loading -> showProgressBar(progressBar, true)
                        is Result.Error -> {
                            showProgressBar(progressBar, false)
                            showSnackBar(root, result.error)
                        }

                        is Result.Success -> {
                            Intent(this@LoginActivity, MainActivity::class.java).apply {
                                startActivity(this)
                                finish()
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val booleanExtra = "Condition"
        const val messageExtra = "Message"
    }
}

class ForgotPasswordBottomSheet : BottomSheetDialogFragment() {

    private var _binding: ForgotPasswordBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ForgotPasswordBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory()
        )[LoginViewModel::class.java]

        setupAction()
    }

    private fun setupAction() {

        binding.submitForgotPasswordBtn.setOnClickListener {
            if (!Patterns.EMAIL_ADDRESS.matcher(binding.emailForgotPasswordTextField.editText?.text.toString())
                    .matches()
            ) {
                binding.emailForgotPasswordTextField.error = getString(R.string.error_unvalid_email)
            } else {
                binding.emailForgotPasswordTextField.apply {
                    error = null
                    isErrorEnabled = false
                }
                viewModel.resetPassword(binding.emailForgotPasswordTextField.editText?.text.toString())
                    .observe(this) { result ->
                        when (result) {
                            is Result.Loading -> { showProgressBar(binding.progressBar, true) }
                            is Result.Error -> {
                                showProgressBar(binding.progressBar, false)
                                showSnackBar(binding.root, result.error)
                            }

                            is Result.Success -> {
                                showProgressBar(binding.progressBar, false)
                                showSnackBar(binding.root, result.data)
                            }
                        }
                    }
            }
        }


    }

    companion object {
        const val TAG = "ForgotBottomSheet"
    }
}
