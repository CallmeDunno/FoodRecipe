package com.example.btl_cnpm.ui.recipe

import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentRecipeBinding
import com.example.btl_cnpm.ui.recipe.adapter.ProcedureRecipeAdapter
import com.example.btl_cnpm.utils.UIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeFragment : BaseFragment<FoodRecipeFragmentRecipeBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_recipe

    private val adapter by lazy { ProcedureRecipeAdapter() }
    private val viewModel by viewModels<RecipeViewModel>()
    private val argument: RecipeFragmentArgs by navArgs()

    override fun initView() {
        super.initView()
        binding.apply {
            headerRecipe.tvHeader.text = ""
            tvIngredientRecipe.movementMethod = ScrollingMovementMethod()
            rcvProcedureRecipe.adapter = adapter
        }
        if (argument.idRecipe != null) {
            with(viewModel) {
                getRecipe(argument.idRecipe).observe(viewLifecycleOwner) {
                    when (it) {
                        is UIState.Success -> {
                            binding.item = it.data
                            val idUser = it.data.idUser
                            val idRecipe = it.data.id
                            viewModel.getOtherElement(idUser, idRecipe)
                        }
                        is UIState.Failure -> {
                            showDialogFail(it.message.toString())
                        }
                    }
                }
                userMutableLiveData.observe(viewLifecycleOwner) {
                    when (it) {
                        is UIState.Success -> {
                            binding.apply {
                                //TODO(EIT HERE)
                            }
                        }
                        is UIState.Failure -> {
                            showDialogFail(it.message.toString())
                        }
                    }
                }
                procedureMutableLiveData.observe(viewLifecycleOwner) {
                    when (it) {
                        is UIState.Success -> {
                            adapter.submitList(it.data)
                        }
                        is UIState.Failure -> {
                            showDialogFail(it.message.toString())
                        }
                    }
                }
            }
        }

//        notify(argument.idRecipe)
    }

    override fun initAction() {
        super.initAction()
        binding.apply {
            headerRecipe.btnBackHeader.setOnClickListener {
                requireView().findNavController().popBackStack()
            }
        }
    }
}