package com.example.btl_cnpm.ui.home

import android.annotation.SuppressLint
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentHomeBinding
import com.example.btl_cnpm.model.CategoryType
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.model.User
import com.example.btl_cnpm.ui.home.adapter.CategoryAdapter
import com.example.btl_cnpm.ui.home.adapter.NewRecipeAdapter
import com.example.btl_cnpm.ui.home.adapter.RecipeAdapter
import com.example.btl_cnpm.utils.SharedPreferencesManager
import com.example.btl_cnpm.utils.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@SuppressLint("SuspiciousIndentation")
@AndroidEntryPoint
class HomeFragment : BaseFragment<FoodRecipeFragmentHomeBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_home

    private val homeViewModel by viewModels<HomeViewModel>()
    private var recipeList = mutableListOf<Recipe>()
    private var newRecipeList = mutableListOf<Recipe>()
    private var userRecipeMap = hashMapOf<Recipe, User>()
    private var userId = ""
    private var userList = arrayListOf<User>()
    private var categoryList = arrayListOf<CategoryType>()

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
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToRecipeFragment(
                    it
                )
            )
        })
    }
    private val recipeAdapter by lazy {
        RecipeAdapter(onItemClick = {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToRecipeFragment(
                    it
                )
            )
        })
    }

    override fun initView() {
        super.initView()
        binding.apply {
            userId = ""

            progress.visibility = View.VISIBLE
            progressNew.visibility = View.VISIBLE
        }
    }

    override fun initAction() {
        super.initAction()
        binding.apply {
            sharedPre.getString("idUserRemember")?.let {
                userId = it
            }
            sharedPre.getString("idUserTemp")?.let {
                userId = it
            }
            rvRecipeCategory.adapter = categoryAdapter
            rvRecipe.adapter = recipeAdapter
            rvNewRecipes.adapter = newRecipeAdapter
            userRecipeMap.clear()
            userList.clear()
            recipeList.clear()
            if (userId.isNotEmpty()) {
                homeViewModel.getUser(userId).observe(requireActivity()) { result ->
                    when (result) {
                        is UIState.Success -> {
                            binding.txtIntro.text = "Hello ${result.data.username}"
                            if (result.data.image.isNotEmpty()) {
                                Glide.with(requireContext()).load(result.data.image)
                                    .into(imgAvatar)
                            }
                        }

                        is UIState.Failure -> {
                            result.message?.let { mes ->
                                showDialogFail(mes)
                            }
                        }
                    }
                }
            } else {
                showDialogFail("Failed to get user")
            }
            homeViewModel.getCategoryType().observe(requireActivity()) {
                when (it) {
                    is UIState.Success -> {
                        categoryList.clear()
                        categoryList.add(CategoryType("T9b6E7ecYMt4Qyq3Pmn0", "All"))
                        categoryList.addAll(it.data)
                        categoryAdapter.submitList(categoryList)
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
                        progress.visibility = View.GONE
                        recipeList = it.data
                        recipeAdapter.submitList(it.data)
                        homeViewModel.getUsers().observe(requireActivity()) { result ->
                            when (result) {
                                is UIState.Success -> {
                                    userList = result.data
                                    runBlocking {
                                        launch {
                                            getUserList()
                                            binding.progressNew.visibility = View.GONE
                                            newRecipeAdapter.submitList(userRecipeMap.entries.toList())
                                        }
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

                    is UIState.Failure -> {
                        it.message?.let { mes ->
                            showDialogFail(mes)
                            progress.visibility = View.GONE
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

    suspend fun getUserList() {
        withContext(Dispatchers.Unconfined) {
            recipeList.forEach { recipe ->
                userList.forEach { user ->
                    if (recipe.idUser == user.id) {
                        userRecipeMap[recipe] = user
                    }
                }
            }
        }
    }
}