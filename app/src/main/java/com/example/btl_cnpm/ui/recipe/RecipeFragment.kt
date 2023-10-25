package com.example.btl_cnpm.ui.recipe

import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentRecipeBinding

class RecipeFragment : BaseFragment<FoodRecipeFragmentRecipeBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_recipe

    override fun initView() {
        super.initView()
        binding.apply {
            headerRecipe.tvHeader.text = ""
        }
    }

    override fun initAction() {
        super.initAction()
        binding.apply {
            rgTabBar.setOnCheckedChangeListener { _, i ->
                when(i){
                    R.id.rbIngredientRecipe -> {
                        rbIngredientRecipe.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                        rbProcedureRecipe.setTextColor(ContextCompat.getColor(requireContext(), R.color.veronese_green))
                    }
                    R.id.rbProcedureRecipe -> {
                        rbIngredientRecipe.setTextColor(ContextCompat.getColor(requireContext(), R.color.veronese_green))
                        rbProcedureRecipe.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    }
                }
            }
        }
    }
}