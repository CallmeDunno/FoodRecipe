package com.example.btl_cnpm.ui.new_recipe

import androidx.navigation.findNavController
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentNewRecipeBinding
import com.example.btl_cnpm.utils.extensions.hide
import com.mcdev.filledboxspinner.OnItemSelectedListener

class NewRecipeFragment : BaseFragment<FoodRecipeFragmentNewRecipeBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_new_recipe

    override fun initView() {
        super.initView()
        binding.apply {
            headerNewRecipe.apply {
                btnMoreHeader.hide()
                tvHeader.text = "New recipe"
            }
            spinnerTypeNewRecipe.isSearchable(false)
            spinnerTypeNewRecipe.setItems(listOf("a", "b", "c"))
        }
    }

    override fun initAction() {
        super.initAction()
        binding.apply {
            spinnerTypeNewRecipe.setOnItemSelectedListener(object : OnItemSelectedListener {
                override fun onItemSelected(itemValue: String) {
                    //write your logic
                }

            })
            headerNewRecipe.btnBackHeader.setOnClickListener {
                requireView().findNavController().popBackStack()
            }
            btnChooseImage.setOnClickListener {

            }
            btnAddProcedureNewRecipe.setOnClickListener { }
        }
    }
}