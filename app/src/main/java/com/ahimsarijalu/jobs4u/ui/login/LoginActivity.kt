package com.ahimsarijalu.jobs4u.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.ahimsarijalu.jobs4u.R
import com.ahimsarijalu.jobs4u.databinding.ActivityLoginBinding
import com.ahimsarijalu.jobs4u.databinding.ForgotPasswordBottomSheetBinding
import com.ahimsarijalu.jobs4u.databinding.FragmentHomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.forgotPasswordBtn.setOnClickListener {
            val bottomSheet = ForgotPasswordBottomSheet()
            bottomSheet.show(supportFragmentManager, ForgotPasswordBottomSheet.TAG)
        }
    }
}

class ForgotPasswordBottomSheet : BottomSheetDialogFragment() {

    private var _binding: ForgotPasswordBottomSheetBinding? = null
    private val binding get() = _binding!!

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
        binding.submitForgotPasswordBtn.setOnClickListener {
            if(!Patterns.EMAIL_ADDRESS.matcher(binding.emailForgotPasswordTextField.editText?.text.toString()).matches()) {
                binding.emailForgotPasswordTextField.error = "Not a valid e-mail"
            } else {
                binding.emailForgotPasswordTextField.error = ""
//                TODO("Call send email reset password")
            }
        }
    }
    companion object {
        const val TAG = "ForgotBottomSheet"
    }
}
