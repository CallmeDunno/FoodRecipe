package com.example.btl_cnpm.ui.profile

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentProfileBinding
import com.example.btl_cnpm.di.SharedPreferencesModule_ProvideSharedPreferencesFactory
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.model.User
import com.example.btl_cnpm.ui.profile.adapter.ProfileRecipeAdapter
import com.example.btl_cnpm.utils.SharedPreferencesManager
import com.example.btl_cnpm.utils.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FoodRecipeFragmentProfileBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_profile

    private val profileViewModel by viewModels<ProfileViewModel>()

    @Inject
    lateinit var sharedPre: SharedPreferencesManager
    private val profileRecipeAdapter by lazy {
        ProfileRecipeAdapter(onItemClick = {

        })
    }
    private var userRecipeMap = hashMapOf<Recipe, User>()

    override fun initView() {
        super.initView()
        binding.rvRecipe.adapter = profileRecipeAdapter
        binding.headerProfile.tvHeader.text = requireContext().getString(R.string.profile)
    }

    override fun initAction() {
        super.initAction()
        binding.apply {
            binding.btnAddRecipe.setOnClickListener {
                requireView().findNavController().navigate(R.id.newRecipeFragment)
            }

            binding.headerProfile.btnBackHeader.setOnClickListener {
                popBackStack()
            }
            sharedPre.getString("idUser")?.let {
                profileViewModel.getUser(it).observe(requireActivity()) { result ->
                    when (result) {
                        is UIState.Success -> {
                            txtProfileName.text = result.data.username
                            if(result.data.image.isNotEmpty()) {
                                Glide.with(requireContext()).load(result.data.image).into(imgProfileAvatar)
                            }
                            if(result.data.bio.isEmpty()) {
                                txtProfileBio.hint = "Thêm tiểu sử"
                            }
                            profileViewModel.getMyRecipes(it)
                                .observe(requireActivity()) { resultRecipe ->
                                    userRecipeMap.clear()
                                    when (resultRecipe) {
                                        is UIState.Success -> {
                                            resultRecipe.data.forEach { recipe ->
                                                userRecipeMap[recipe] = result.data
                                            }
                                            profileRecipeAdapter.submitList(userRecipeMap.entries.toList())
                                            txtProfileJob.text = "Recipe: ${userRecipeMap.size}"
                                        }

                                        is UIState.Failure -> {
                                            resultRecipe.message?.let { mes ->
                                                showDialogFail(mes)
                                            }
                                        }
                                    }
                                }
                        }

                        is UIState.Failure -> {
                            result.message?.let { mes ->
                                showDialogFail(mes)
                            }
                            Log.d("tung", "fail")
                        }
                    }

                }
            }

        }


    }
}