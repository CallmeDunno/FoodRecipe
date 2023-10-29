package com.example.btl_cnpm.ui.sign_up

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Patterns
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentSignupBinding
import com.example.btl_cnpm.ui.sign_in.SignInViewModel
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
                val confirmpassword = edtConfirmPassword.text.toString().trim()
                if(!isValidated(username, email, password, confirmpassword)) {
                    showDialogSignUpFail("Please fill in your information")
                    return@setOnClickListener
                }
                if(password.contentEquals(edtConfirmPassword.text)) {
                    if(Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        signUpViewModel.signUp(username, email, password).observe(requireActivity()) {
                            when(it) {
                                is UIState.Success -> {
                                    requireView().findNavController().navigate(R.id.signInFragment)
                                }
                                is UIState.Failure -> {
                                    showDialogSignUpFail(it.message!!)
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

    private fun isValidated(username: String, email: String, password: String, confirmpassword: String): Boolean {
        if(username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmpassword.isEmpty()) {
            showDialogSignUpFail("Please fill in your information")
            return false
        }
        return true
    }
    private fun showDialogSignUpFail(ex: String) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.food_recipe_dialog_sign_in_failure)
        val txtSignUpFail = dialog.findViewById<TextView>(R.id.tvContentError)
        txtSignUpFail.text = ex
        dialog.setCanceledOnTouchOutside(false)
        val window = dialog.window ?: return
        window.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.findViewById<Button>(R.id.btnTryAgainDialog).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

}