package com.example.btl_cnpm.ui.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import androidx.navigation.fragment.findNavController
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentHomeBinding
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.model.User
import com.example.btl_cnpm.ui.home.adapter.CategoryAdapter
import com.example.btl_cnpm.ui.home.adapter.NewRecipeAdapter
import com.example.btl_cnpm.ui.home.adapter.RecipeAdapter
import com.example.btl_cnpm.utils.SharedPreferencesManager
import com.example.btl_cnpm.utils.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
    private var user = User()
    private var userId = ""
    private var userList = arrayListOf<User>()

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
            rvRecipeCategory.adapter = categoryAdapter
            rvRecipe.adapter = recipeAdapter
            rvNewRecipes.adapter = newRecipeAdapter
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
            if (userId.isNotEmpty()) {
                homeViewModel.getUser(userId).observe(requireActivity()) { result ->
                    when (result) {
                        is UIState.Success -> {
                            user = result.data
                            binding.txtIntro.text = "Hello ${user.username}"
                            if (user.image.isNotEmpty()) {
                                Glide.with(requireContext()).load(user.image)
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
                        recipeAdapter.submitList(recipeList)
                        progress.visibility = View.GONE
                        homeViewModel.getUsers().observe(requireActivity()) { result ->
                            when (result) {
                                is UIState.Success -> {
                                    userList = result.data
                                    Log.d("tung", "get all data")
                                    runBlocking {
                                        launch {
                                            getUserList()
                                            Log.d("tung", "get all")
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