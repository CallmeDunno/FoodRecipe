package com.example.btl_cnpm.ui.splash

import android.content.SharedPreferences
import androidx.navigation.findNavController
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment<FoodRecipeFragmentSplashBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_splash

    @Inject lateinit var sharedPre: SharedPreferences

    override fun initAction() {
        super.initAction()
        binding.btnStartSplash.setOnClickListener {
            //TODO("CHECK DATABASE LOCAL")
//            requireView().findNavController().popBackStack(R.id.login_navigation, true)
            requireView().findNavController().navigate(R.id.signInFragment)
//            requireView().findNavController().navigate(R.id.home_navigation)
        }
    }
}