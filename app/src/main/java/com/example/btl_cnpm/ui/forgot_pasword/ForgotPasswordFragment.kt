package com.example.btl_cnpm.ui.forgot_pasword

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentForgotPasswordBinding
import com.example.btl_cnpm.utils.extensions.hide
import com.example.btl_cnpm.utils.extensions.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<FoodRecipeFragmentForgotPasswordBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_forgot_password
    val viewModel: ForgotPasswordViewModel by viewModels()

    override fun initView() {
        super.initView()
        with(binding.headerForgotPassword) {
            btnMoreHeader.hide()
            tvHeader.text = "Forgot Password"
        }
    }

    override fun initAction() {
        super.initAction()
        binding.apply {
            layoutForgotPassword.setOnClickListener {
                it.hideKeyboard()
                edtemailForgotpassword.clearFocus()
            }
            headerForgotPassword.btnBackHeader.setOnClickListener {
                requireView().findNavController().popBackStack()
            }
            tvSignUpFP.setOnClickListener {
                requireView().findNavController().popBackStack(R.id.forgotPasswordFragment, true)
                requireView().findNavController().navigate(R.id.signUpFragment)
            }
            btnForgotPassword.setOnClickListener {
                val email = edtemailForgotpassword.text.toString()
                if (email.isNotEmpty()) {
                    viewModel.sendResetPasswordEmail(email)
                } else {
                    Toast.makeText(requireContext(), "Please enter Email.", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isResetEmailSent.observe(viewLifecycleOwner, Observer { isSent ->
            if (isSent) {
                Toast.makeText(requireContext(), "Password reset email has been sen.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Unable to send email, please check again.", Toast.LENGTH_SHORT).show()
            }
        })
    }

}