package com.example.btl_cnpm.ui.new_recipe

import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentNewRecipeBinding
import com.example.btl_cnpm.model.CategoryType
import com.example.btl_cnpm.model.Procedure
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.ui.new_recipe.adapter.SpinnerAdapter
import com.example.btl_cnpm.utils.SharedPreferencesManager
import com.example.btl_cnpm.utils.UIState
import com.example.btl_cnpm.utils.extensions.hide
import com.example.btl_cnpm.utils.extensions.hideKeyboard
import com.example.btl_cnpm.utils.extensions.show
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class NewRecipeFragment : BaseFragment<FoodRecipeFragmentNewRecipeBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_new_recipe

    private val adapter by lazy { SpinnerAdapter(requireContext(), categoryTypeList) }
    private val viewModel by viewModels<NewRecipeViewModel>()
    private lateinit var categoryTypeList: List<CategoryType>
    private var uriImage : String = ""
    private lateinit var idUser: String
    private var idCategoryType: String = ""
    private var idEditText = 1
    private var index = 1
    private val lEditText = ArrayList<EditText>()
    private val lProcedure = ArrayList<Procedure>()
    @Inject lateinit var sharedPre: SharedPreferencesManager
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                uriImage = uri.toString()
                Glide.with(requireContext()).load(uri).into(binding.imgNewRecipe)
                binding.tvChooseImage.hide()
            } else {
                binding.tvChooseImage.show()
            }
        }

    override fun initVariable() {
        super.initVariable()
        idUser = if (!sharedPre.getString("idUserTemp").isNullOrEmpty())
            sharedPre.getString("idUserTemp")!!
        else sharedPre.getString("idUserRemember")!!
    }

    override fun initView() {
        super.initView()
        binding.apply {
            headerNewRecipe.apply {
                btnMoreHeader.hide()
                tvHeader.text = "New recipe"
            }
            viewModel.getCategoryType().observe(viewLifecycleOwner){
                when(it) {
                    is UIState.Success -> {
                        categoryTypeList = it.data
                        spinnerTypeNewRecipe.adapter = adapter
                    }
                    is UIState.Failure -> {}
                }
            }
            viewModel.downloadUri.observe(viewLifecycleOwner){
                when(it){
                    is UIState.Success -> {
                        Log.d("Dunno", "Done: ${it.data}")  //get URL
                        val name = edtNameNewRecipe.text.toString().trim()
                        val timer = isValidNumber(edtTimeNewRecipe.text.toString().trim())
                        val ingredient = edtIngredientNewRecipe.text.toString().trim()
                        for (e in lEditText){
                            val text = e.text.toString().trim()
                            if (!TextUtils.isEmpty(text)){
                                lProcedure.add(Procedure(index++, text))
                            }
                        }
                        if (isValid(name) && isValid(ingredient) && isValid(lProcedure) && idCategoryType.isNotEmpty()) {
                            val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
                            val recipe = Recipe(name, idCategoryType, idUser, ingredient, date, it.data, timer, 0f)
                            viewModel.addNewRecipe(recipe, lProcedure)
                        }
                    }
                    is UIState.Failure -> {
                        Log.e("Dunno", it.message.toString())
                    }
                }
            }
            viewModel.stateCreateRecipe.observe(viewLifecycleOwner){
                if (it){
                    notify("Create new recipe successfully!")
                    requireView().findNavController().popBackStack()
                }
            }
        }
    }

    override fun initAction() {
        super.initAction()
        binding.apply {
            layoutNewRecipe.setOnClickListener {
                it.hideKeyboard()
                edtIngredientNewRecipe.clearFocus()
                edtIngredientNewRecipe.clearFocus()
                edtTimeNewRecipe.clearFocus()
                if (lEditText.isNotEmpty()){
                    for (e in lEditText){
                        e.clearFocus()
                    }
                }
            }

            headerNewRecipe.btnBackHeader.setOnClickListener {
                requireView().findNavController().popBackStack()
            }

            cvImageNewRecipe.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

            spinnerTypeNewRecipe.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val clickedItem = parent?.getItemAtPosition(position) as CategoryType
                    idCategoryType = clickedItem.id
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

            btnAddNewRecipe.setOnClickListener {
                if (isValid(uriImage)){
                    viewModel.uploadImage(Uri.parse(uriImage))
                    notify("Please wait a moment........")
                }
            }

            btnAddProcedureNewRecipe.setOnClickListener {
                val textInputLayout = TextInputLayout(requireContext())
                textInputLayout.hint = "Step $idEditText"
                textInputLayout.hintTextColor = ContextCompat.getColorStateList(requireContext(), R.color.veronese_green)
                val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                layoutParams.setMargins(20, 10, 20, 10)
                textInputLayout.layoutParams = layoutParams
                binding.layoutProcedure.addView(textInputLayout)

                val editText = EditText(requireContext())
                editText.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
                editText.setPadding(20, 30, 20, 30)
                editText.setTextColor(resources.getColor(R.color.black))
                editText.textSize = 16f
                editText.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_white_stroke_1)
                editText.requestFocus()
                textInputLayout.addView(editText)

                lEditText.add(editText)
                idEditText++
            }
        }
    }

    private fun isValid(text: String?): Boolean {
        if (!text.isNullOrEmpty()) return true
        Toast.makeText(requireContext(), "The information cannot be left blank", Toast.LENGTH_SHORT).show()
        return false
    }

    private fun isValidNumber(text: String?): Int {
        if (!text.isNullOrEmpty() && text.toInt() > 0) return text.toInt()
        Toast.makeText(requireContext(), "The information cannot be left blank or invalid", Toast.LENGTH_SHORT).show()
        return 0
    }

    private fun isValid(list: ArrayList<Procedure>): Boolean {
        if (list.isNotEmpty()) return true
        Toast.makeText(requireContext(), "The information cannot be left blank", Toast.LENGTH_SHORT).show()
        return false
    }

}