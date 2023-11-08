package com.example.btl_cnpm.ui.recipe

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Gravity
import android.view.View
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
import com.example.btl_cnpm.data.local.BookmarkLocal
import com.example.btl_cnpm.databinding.FoodRecipeFragmentRecipeBinding
import com.example.btl_cnpm.model.Procedure
import com.example.btl_cnpm.ui.recipe.adapter.ProcedureRecipeAdapter
import com.example.btl_cnpm.utils.SharedPreferencesManager
import com.example.btl_cnpm.utils.UIState
import com.example.btl_cnpm.utils.extensions.show
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment : BaseFragment<FoodRecipeFragmentRecipeBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_recipe

    private val adapter by lazy { ProcedureRecipeAdapter() }
    private val viewModel by viewModels<RecipeViewModel>()
    private val argument: RecipeFragmentArgs by navArgs()
    private lateinit var idRecipe: String
    private lateinit var idUser: String

    @Inject lateinit var sharedPre: SharedPreferencesManager

    override fun initVariable() {
        super.initVariable()
        idRecipe = argument.idRecipe
        idUser = if (!sharedPre.getString("idUserTemp").isNullOrEmpty())
            sharedPre.getString("idUserTemp")!!
        else sharedPre.getString("idUserRemember")!!
    }

    override fun initView() {
        super.initView()
        binding.apply {
            rcvProcedureRecipe.adapter = adapter
        }
        with(viewModel) {
            getRecipe(idRecipe).observe(viewLifecycleOwner) {
                when (it) {
                    is UIState.Success -> {
                        binding.headerRecipe.tvHeader.text = "${it.data.name}"
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
            stateRate.observe(viewLifecycleOwner) {
                when (it) {
                    is UIState.Success -> {
                        notify(it.data)
                    }
                    is UIState.Failure -> {
                        notify(it.message.toString())
                    }
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
                                val recipe = binding.item!!
                                val idRecipe = recipe.id
                                val name = recipe.name
                                val creator = tvAuthorNameRecipe.text.toString()
                                val timer = recipe.timer
                                val rate = recipe.rate
                                val ingredient = recipe.ingredient
                                val image = recipe.image
                                val lProcedure = adapter.currentList
                                val procedure = Gson().toJson(lProcedure)
                                val bookmarkLocal = BookmarkLocal(null, idRecipe, idUser, name, creator, timer, rate, ingredient, image, procedure)
                                notify("Saved to bookmark successful!")
                                viewModel.saveToBookmark(bookmarkLocal)
                            }
                        }
                        true
                    }
                    popUp.show()
                }
            }
            btnFollowRecipe.setOnClickListener {

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
        val buttonSend = dialog.findViewById<Button>(R.id.btnSendRatingBar)

        buttonSend.visibility = View.INVISIBLE
        viewModel.checkExistsRateByUser(idRecipe, idUser)
            .observe(viewLifecycleOwner) { isExist ->
                when (isExist) {
                    is UIState.Success -> {
                        val (rate, idRate) = isExist.data
                        ratingBar.rating = rate.toFloat()
                        buttonSend.tag = rate
                        ratingBar.tag = idRate
                        buttonSend.show()
                    }
                    is UIState.Failure -> { Log.e("Dunno", isExist.message.toString()) }
                }
            }
        buttonSend.setOnClickListener {
            val rate = ratingBar.rating.toInt()
            val idRate = ratingBar.tag.toString()
            if (buttonSend.tag.toString().toInt() > 0) {
                viewModel.updateRate(idRecipe, idRate, rate)
            } else {
                viewModel.insertRate(idRecipe, idUser, rate)
            }
            viewModel.stateRate.observe(viewLifecycleOwner) {
                when (it) {
                    is UIState.Success -> { notify(it.data) }
                    is UIState.Failure -> { notify(it.message.toString()) }
                }
            }
            dialog.dismiss()
        }
        dialog.show()
    }

}