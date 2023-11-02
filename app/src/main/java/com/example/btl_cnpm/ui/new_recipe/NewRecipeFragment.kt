package com.example.btl_cnpm.ui.new_recipe

import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentNewRecipeBinding
import com.example.btl_cnpm.utils.GlideImageLoader
import com.example.btl_cnpm.utils.extensions.hide
import lv.chi.photopicker.PhotoPickerFragment

class NewRecipeFragment : BaseFragment<FoodRecipeFragmentNewRecipeBinding>(), PhotoPickerFragment.Callback {
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
                PhotoPickerFragment.newInstance().show(requireActivity().supportFragmentManager, "Tag")
            }
            btnAddProcedureNewRecipe.setOnClickListener { }
        }
    }

    override fun onImagesPicked(photos: ArrayList<Uri>) {
        GlideImageLoader().loadImage(requireContext(), binding.imgNewRecipe, photos[0])
    }
}