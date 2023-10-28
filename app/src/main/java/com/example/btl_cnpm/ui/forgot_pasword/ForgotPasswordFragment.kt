package com.example.btl_cnpm.ui.forgot_pasword

import androidx.navigation.findNavController
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentForgotPasswordBinding
import com.example.btl_cnpm.utils.extensions.hide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<FoodRecipeFragmentForgotPasswordBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_forgot_password

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
            headerForgotPassword.btnBackHeader.setOnClickListener {
                requireView().findNavController().popBackStack()
            }
            tvSignUpFP.setOnClickListener {
                requireView().findNavController().popBackStack(R.id.forgotPasswordFragment, true)
                requireView().findNavController().navigate(R.id.signUpFragment)
            }
            btnForgotPassword.setOnClickListener {
                //TODO("REQUEST SERVER SEND VERIFY CODE TO EMAIL")
            }
        }
    }

}