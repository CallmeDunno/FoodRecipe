package com.example.btl_cnpm.ui.sign_up


import android.util.Patterns

import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentSignupBinding
import com.example.btl_cnpm.utils.UIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FoodRecipeFragmentSignupBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_signup

    private val signUpViewModel by viewModels<SignUpViewModel>()

    override fun initView() {
        super.initView()
    }

    override fun initAction() {
        super.initAction()
        binding.apply {
            btnSignUp.setOnClickListener {
                val username = edtUsername.text.toString().trim()
                val password = edtPassword.text.toString().trim()
                val email = edtEmail.text.toString().trim()
                val confirmPassword = edtConfirmPassword.text.toString().trim()
                if(!isValidated(username, email, password, confirmPassword)) {
                    return@setOnClickListener
                }
                if(password.contentEquals(edtConfirmPassword.text)) {
                    if(Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        signUpViewModel.signUp(email, password).observe(requireActivity()) {
                            when(it) {
                                is UIState.Success -> {
                                    notify(requireContext().getString(R.string.sign_up_successfully))
                                    signUpViewModel.createUser(it.data[0], username, email, password, it.data[1]).observe(requireActivity()) { result ->
                                        when(result) {
                                            is UIState.Success -> {
                                                requireView().findNavController().navigate(R.id.signInFragment)
                                            }
                                            is UIState.Failure -> {
                                                result.message?.let {mes ->
                                                    showDialogFail(mes)
                                                }
                                            }
                                        }
                                    }
                                }
                                is UIState.Failure -> {
                                    it.message?.let {mes ->
                                        showDialogFail(mes)

                                    }
                                }
                            }
                        }
                    } else {
                        binding.edtEmail.error = requireContext().getString(R.string.email_is_not_valid)
                        return@setOnClickListener
                    }
                } else {
                    edtConfirmPassword.error = requireContext().getString(R.string.re_enter_your_password)
                    return@setOnClickListener
                }
            }

            txtSignIn.setOnClickListener {
                requireView().findNavController().popBackStack(R.id.signUpFragment, true)
                requireView().findNavController().navigate(R.id.signInFragment)
            }
        }
    }

    private fun isValidated(username: String, email: String, password: String, confirmPassword: String): Boolean {
        if(username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showDialogFail(requireContext().getString(R.string.fill_in_information))
            return false
        }
        return true
    }

}