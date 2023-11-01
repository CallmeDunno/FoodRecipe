package com.example.btl_cnpm.ui.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.btl_cnpm.FoodRecipeApplication
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentHomeBinding
import com.example.btl_cnpm.di.SharedPreferencesModule
import com.example.btl_cnpm.di.SharedPreferencesModule_ProvideSharedPreferencesFactory
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.model.User
import com.example.btl_cnpm.ui.home.adapter.CategoryAdapter
import com.example.btl_cnpm.ui.home.adapter.NewRecipeAdapter
import com.example.btl_cnpm.ui.home.adapter.RecipeAdapter
import com.example.btl_cnpm.utils.SharedPreferencesManager
import com.example.btl_cnpm.utils.UIState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("SuspiciousIndentation")
@AndroidEntryPoint
class HomeFragment : BaseFragment<FoodRecipeFragmentHomeBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_home

    private val homeViewModel by viewModels<HomeViewModel>()
    private var recipeList = mutableListOf<Recipe>()
    private var newRecipeList = mutableListOf<Recipe>()

    @Inject
    lateinit var sharedPre: SharedPreferencesManager
    private val categoryAdapter by lazy {
        CategoryAdapter(onItemClick = {
            if (it.contentEquals("T9b6E7ecYMt4Qyq3Pmn0")) {
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

        })
    }
    private val recipeAdapter by lazy {
        RecipeAdapter(onItemClick = {

        })
    }

    override fun initView() {
        super.initView()
        binding.apply {
            rvRecipeCategory.adapter = categoryAdapter
            rvRecipe.adapter = recipeAdapter
            rvNewRecipes.adapter = newRecipeAdapter
            progress.visibility = View.VISIBLE
        }
    }
    override fun initAction() {
        super.initAction()
        binding.apply {
            sharedPre.getString("idUser")?.let {
                homeViewModel.getUser(it).observe(requireActivity()) {result ->
                    when (result) {
                        is UIState.Success -> {
                                binding.txtIntro.text = "Hello ${result.data.username}"
                                result.data.image?.let { img ->
                                    Glide.with(requireContext()).load(img).into(imgAvatar)
                                }
                        }
                        is UIState.Failure -> {
                            result.message?.let { mes ->
                                showDialogFail(mes)
                            }
                        }
                    }
                }
            }

            homeViewModel.getCategory().observe(requireActivity()) {
                when (it) {
                    is UIState.Success -> {
                        categoryAdapter.submitList(it.data)
                    }

                    is UIState.Failure -> {
                        it.message?.let { mes ->
                            showDialogFail(mes)
                        }
                    }
                }
            }
            homeViewModel.getRecipes().observe(requireActivity()) {
                when (it) {
                    is UIState.Success -> {
                        recipeList = it.data
                        recipeAdapter.submitList(it.data)
                        progress.visibility = View.GONE
                    }

                    is UIState.Failure -> {
                        it.message?.let { mes ->
                            showDialogFail(mes)
                            progress.visibility = View.GONE
                        }
                    }
                }
            }

            homeViewModel.getRecipeUserList().observe(requireActivity()) {
                when (it) {
                    is UIState.Success -> {
                        Log.d("tung", "get all")
                        newRecipeAdapter.submitList(it.data.entries.toList())
                    }

                    is UIState.Failure -> {
                        Log.d("tung", "cant get")
                        it.message?.let { mes ->
                            showDialogFail(mes)
                        }
                    }
                }
            }


            btnFilter.setOnClickListener {
                    val bundle = bundleOf("edtSearch" to edtSearch.text.toString())
                    requireView().findNavController().navigate(R.id.searchFragment, bundle)
            }
        }

    }
}