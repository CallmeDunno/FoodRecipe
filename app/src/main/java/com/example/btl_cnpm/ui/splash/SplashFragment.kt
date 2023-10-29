package com.example.btl_cnpm.ui.splash

import androidx.navigation.findNavController
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentSplashBinding
import com.example.btl_cnpm.utils.SharedPreferencesManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment<FoodRecipeFragmentSplashBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_splash

    @Inject
    lateinit var sharedPre: SharedPreferencesManager

    override fun initAction() {
        super.initAction()
        binding.btnStartSplash.setOnClickListener {
            if (sharedPre.getString("idUser").isNullOrEmpty()) {
                requireView().findNavController().navigate(R.id.signInFragment)
            } else {
                requireView().findNavController().popBackStack(R.id.login_navigation, true)
                requireView().findNavController().navigate(R.id.home_navigation)
            }
        }
    }
}