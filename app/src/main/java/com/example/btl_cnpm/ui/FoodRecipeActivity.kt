package com.example.btl_cnpm.ui

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseActivity
import com.example.btl_cnpm.databinding.FoodRecipeActivityMainBinding
import com.example.btl_cnpm.utils.extensions.hide
import com.example.btl_cnpm.utils.extensions.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodRecipeActivity : BaseActivity<FoodRecipeActivityMainBinding>() {
    override val layoutID = R.layout.food_recipe_activity_main

    private var _navController: NavController? = null
    private val navController get() = _navController!!

    override fun initView() {
        super.initView()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        _navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navHostFragment.navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment,
                R.id.signInFragment,
                R.id.signUpFragment -> binding.bottomNavigation.hide()
                else -> binding.bottomNavigation.show()
            }
        }
    }
}