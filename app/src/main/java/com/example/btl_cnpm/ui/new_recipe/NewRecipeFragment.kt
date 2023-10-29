package com.example.btl_cnpm.ui.new_recipe

import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentNewRecipeBinding
import com.example.btl_cnpm.utils.extensions.hide

class NewRecipeFragment : BaseFragment<FoodRecipeFragmentNewRecipeBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_new_recipe
    private val viewModel by viewModels<NewRecipeViewModel>()

    override fun initView() {
        super.initView()
        binding.apply {
            headerNewRecipe.apply {
                btnMoreHeader.hide()
                tvHeader.text = "New recipe"
            }
        }
    }

    override fun initAction() {
        super.initAction()
        binding.apply {
            headerNewRecipe.btnBackHeader.setOnClickListener {
                requireView().findNavController().popBackStack()
            }
            btnChooseImage.setOnClickListener {

            }
            btnAddProcedureNewRecipe.setOnClickListener { }
        }
    }
}