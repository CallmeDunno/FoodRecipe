package com.example.btl_cnpm.ui.sign_in

import androidx.navigation.findNavController
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentSigninBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FoodRecipeFragmentSigninBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_signin

    override fun initView() {
        super.initView()
    }

    override fun initAction() {
        super.initAction()
        binding.apply {
            tvSignUp.setOnClickListener {
                requireView().findNavController().popBackStack(R.id.signInFragment, true)
                requireView().findNavController().navigate(R.id.signUpFragment)
            }
            btnSignIn.setOnClickListener {
                //TODO("COMPARE ACCOUNT WITH DATABASE")
                requireView().findNavController().popBackStack(R.id.login_navigation, true)
                requireView().findNavController().navigate(R.id.home_navigation)
            }
            tvForgetPasswordSignIn.setOnClickListener {
                requireView().findNavController().navigate(R.id.forgotPasswordFragment)
            }
        }
    }
}