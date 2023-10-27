package com.example.btl_cnpm.ui.sign_up

import androidx.navigation.findNavController
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FoodRecipeFragmentSignupBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_signup

    @Inject lateinit var fAuth: FirebaseAuth
    @Inject lateinit var fFireStore: FirebaseFirestore

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