package com.example.btl_cnpm.ui.home

import android.util.Log
import androidx.fragment.app.viewModels
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentHomeBinding
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.ui.home.adapter.CategoryAdapter
import com.example.btl_cnpm.ui.home.adapter.RecipeAdapter
import com.example.btl_cnpm.ui.sign_in.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FoodRecipeFragmentHomeBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_home

    private val homeViewModel by viewModels<HomeViewModel>()
    private var recipeList = arrayListOf<Recipe>()
    private val categoryAdapter by lazy {
        CategoryAdapter(requireContext(), onItemClick = {
            if(it.contentEquals("T9b6E7ecYMt4Qyq3Pmn0")) {
                recipeAdapter.submitList(recipeList)
            } else
            recipeAdapter.submitList(recipeList.filter {recipe ->
                recipe.idCategory.contentEquals(it)
            })
        })
    }
    private val recipeAdapter by lazy {
        RecipeAdapter(requireContext(), onItemClick = {

        })
    }
    override fun initAction() {
        super.initAction()
        binding.apply {
            rvRecipeCategory.adapter = categoryAdapter
            rvRecipe.adapter = recipeAdapter

            homeViewModel.getCategory().observe(requireActivity()) {
                categoryAdapter.submitList(it)
            }

            homeViewModel.getRecipes().observe(requireActivity()) {
                recipeList = it
                recipeAdapter.submitList(it)
            }
        }

    }
}