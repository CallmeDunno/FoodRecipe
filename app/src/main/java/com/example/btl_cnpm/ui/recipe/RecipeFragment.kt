package com.example.btl_cnpm.ui.recipe

import android.text.method.ScrollingMovementMethod
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentRecipeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeFragment : BaseFragment<FoodRecipeFragmentRecipeBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_recipe

    override fun initView() {
        super.initView()
        binding.apply {
            headerRecipe.tvHeader.text = ""
            tvIngredientRecipe.movementMethod = ScrollingMovementMethod()
        }
    }

    override fun initAction() {
        super.initAction()
    }
}