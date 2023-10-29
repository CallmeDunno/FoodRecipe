package com.example.btl_cnpm.ui.profile

import androidx.navigation.findNavController
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FoodRecipeFragmentProfileBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_profile

    override fun initAction() {
        super.initAction()
        binding.btnAddRecipe.setOnClickListener {
            requireView().findNavController().navigate(R.id.newRecipeFragment)
        }
    }
}