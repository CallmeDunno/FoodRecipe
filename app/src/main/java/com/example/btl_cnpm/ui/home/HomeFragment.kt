package com.example.btl_cnpm.ui.home

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentHomeBinding
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.ui.home.adapter.CategoryAdapter
import com.example.btl_cnpm.ui.home.adapter.NewRecipeAdapter
import com.example.btl_cnpm.ui.home.adapter.RecipeAdapter
import com.example.btl_cnpm.utils.UIState
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("SuspiciousIndentation")
@AndroidEntryPoint
class HomeFragment : BaseFragment<FoodRecipeFragmentHomeBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_home

    private val homeViewModel by viewModels<HomeViewModel>()
    private var recipeList = mutableListOf<Recipe>()
    private var newRecipeList = mutableListOf<Recipe>()
    private val categoryAdapter by lazy {
        CategoryAdapter(onItemClick = {
            if(it.contentEquals("T9b6E7ecYMt4Qyq3Pmn0")) {
                recipeAdapter.submitList(recipeList)
            } else {
                newRecipeList = recipeList.filter { recipe ->
                    recipe.idCategoryType.contentEquals(it)
                }.toMutableList()
                if (newRecipeList.isEmpty()) {
                    recipeAdapter.submitList(recipeList)
                } else {
                    recipeAdapter.submitList(newRecipeList)
                }
            }
        })
    }
    private val newRecipeAdapter by lazy {
        NewRecipeAdapter(onItemCLick = {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToRecipeFragment(it))
        })
    }
    private val recipeAdapter by lazy {
        RecipeAdapter(onItemClick = {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToRecipeFragment(it))
        })
    }
    override fun initAction() {
        super.initAction()
        binding.apply {
            rvRecipeCategory.adapter = categoryAdapter
            rvRecipe.adapter = recipeAdapter
            rvNewRecipes.adapter = newRecipeAdapter
            homeViewModel.getCategory().observe(requireActivity()) {
                when(it) {
                    is UIState.Success -> {
                        categoryAdapter.submitList(it.data)
                    }
                    is UIState.Failure -> {
                        it.message?.let {mes ->
                            showDialogFail(mes)
                        }
                    }
                }
            }

            homeViewModel.getRecipes().observe(requireActivity()) {
                when(it) {
                    is UIState.Success -> {
                        recipeList = it.data
                        recipeAdapter.submitList(it.data)
                        newRecipeAdapter.submitList(it.data)
                    }
                    is UIState.Failure -> {
                        it.message?.let {mes ->
                            showDialogFail(mes)
                        }
                    }
                }
            }
        }

    }
}