package com.example.btl_cnpm.ui.sign_up

import androidx.navigation.findNavController
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentSignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FoodRecipeFragmentSignupBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_signup

    override fun initView() {
        super.initView()
    }

    override fun initAction() {
        super.initAction()
        binding.apply {
            btnSignup.setOnClickListener {
                //TODO("ADD DATA TO USER TABLE")
            }
            tvSignIn.setOnClickListener {
                requireView().findNavController().popBackStack(R.id.signUpFragment, true)
                requireView().findNavController().navigate(R.id.signInFragment)
            }
        }
    }

}