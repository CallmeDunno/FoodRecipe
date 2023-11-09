package com.example.btl_cnpm.ui.profile

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
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
    private var userId = ""

    @Inject
    lateinit var sharedPre: SharedPreferencesManager
    private val profileRecipeAdapter by lazy {
        ProfileRecipeAdapter(onItemClick = {

        })
    }
    private var userRecipeMap = hashMapOf<Recipe, User>()

    override fun initView() {
        super.initView()
        userId = ""
        binding.rvRecipe.adapter = profileRecipeAdapter
        binding.headerProfile.tvHeader.text = requireContext().getString(R.string.profile)
        binding.headerProfile.btnBackHeader.visibility = View.GONE
    }

    override fun initAction() {
        super.initAction()
        binding.apply {
            binding.btnAddRecipe.setOnClickListener {
                requireView().findNavController().navigate(R.id.newRecipeFragment)
            }

            headerProfile.btnMoreHeader.setOnClickListener{
                showDialogOption()
            }
            sharedPre.getString("idUserRemember")?.let {
                userId = it
            }
            sharedPre.getString("idUserTemp")?.let {
                userId = it
            }
            if(userId.isNotEmpty()) {
                profileViewModel.getUser(userId).observe(requireActivity()) { result ->
                    when (result) {
                        is UIState.Success -> {
                            txtProfileName.text = result.data.username
                            txtProfileBio.text = result.data.bio
                            if(result.data.image.isNotEmpty()) {
                                Glide.with(requireContext()).load(result.data.image).into(imgProfileAvatar)
                            }
                            profileViewModel.getMyRecipes(userId)
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
            } else {
                showDialogFail("Fail to get user")
            }
        }
    }

    fun showDialogOption() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.food_recipe_dialog_edit_sign_out)
        val window = dialog.window ?: return
        val btnEdit = dialog.findViewById<AppCompatButton>(R.id.btn_edit)
        val btnSignOut = dialog.findViewById<AppCompatButton>(R.id.btn_sign_out)

        btnEdit.setOnClickListener {
            requireView().findNavController().navigate(R.id.editProfileFragment2)
            dialog.dismiss()
        }

        btnSignOut.setOnClickListener {
            sharedPre.removeKey("idUserTemp")
            sharedPre.removeKey("idUserRemember")
            dialog.dismiss()
            requireView().findNavController().navigate(R.id.login_navigation)
        }
        window.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.show()
    }
}