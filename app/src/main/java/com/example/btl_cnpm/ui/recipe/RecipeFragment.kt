package com.example.btl_cnpm.ui.recipe

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.method.ScrollingMovementMethod
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.PopupMenu
import android.widget.RatingBar
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentRecipeBinding
import com.example.btl_cnpm.model.Procedure
import com.example.btl_cnpm.ui.recipe.adapter.ProcedureRecipeAdapter
import com.example.btl_cnpm.utils.SharedPreferencesManager
import com.example.btl_cnpm.utils.UIState
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment : BaseFragment<FoodRecipeFragmentRecipeBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_recipe

    private val adapter by lazy { ProcedureRecipeAdapter() }
    private val viewModel by viewModels<RecipeViewModel>()
    private val argument: RecipeFragmentArgs by navArgs()

    @Inject
    lateinit var sharedPre: SharedPreferencesManager

    override fun initView() {
        super.initView()
        binding.apply {
            headerRecipe.tvHeader.text = "Recipe"
            tvIngredientRecipe.movementMethod = ScrollingMovementMethod()
            rcvProcedureRecipe.adapter = adapter
        }
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
                            Glide.with(requireView()).load(it.data.image)
                                .into(imgAvatarAuthorRecipe)
                            tvAuthorNameRecipe.text = it.data.username
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
                        Collections.sort(it.data, Procedure.Companion.SortByIndex())
                        adapter.submitList(it.data)
                    }
                    is UIState.Failure -> {
                        showDialogFail(it.message.toString())
                    }
                }
            }
            stateRate.observe(viewLifecycleOwner){
                when(it){
                    is UIState.Success -> {
                        notify(it.data)
                    }
                    is UIState.Failure -> {notify(it.message.toString())}
                }
            }
        }
    }

    override fun initAction() {
        super.initAction()
        binding.apply {
            headerRecipe.apply {
                btnBackHeader.setOnClickListener {
                    requireView().findNavController().popBackStack()
                }
                btnMoreHeader.setOnClickListener {
                    val popUp = PopupMenu(requireContext(), it)
                    popUp.menuInflater.inflate(R.menu.menu_more, popUp.menu)
                    popUp.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.rateItemMore -> {
                                showRatingDialog()
                            }
                            R.id.saveItemMore -> {
                                notify("Save")
                            }
                        }
                        true
                    }
                    popUp.show()
                }
            }
        }
    }

    private fun showRatingDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.food_recipe_dialog_rate)
        dialog.setCanceledOnTouchOutside(true)
        val window = dialog.window ?: return
        window.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val ratingBar = dialog.findViewById<RatingBar>(R.id.ratingBar)
        dialog.findViewById<Button>(R.id.btnSendRatingBar).setOnClickListener {
            val idUser = if (!sharedPre.getString("idUserTemp").isNullOrEmpty())
                sharedPre.getString("idUserTemp")!!
            else sharedPre.getString("idUserRemember")!!
            val rate = ratingBar.rating.toInt()
            viewModel.updateRate(argument.idRecipe, idUser, rate)
            dialog.dismiss()
        }
        dialog.show()
    }


}