package com.example.btl_cnpm.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentSearchBinding
import com.example.btl_cnpm.model.Category
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.ui.home.adapter.CategoryAdapter
import com.example.btl_cnpm.ui.search.adapter.FilterAdapter
import com.example.btl_cnpm.ui.search.adapter.SearchRecipeAdapter
import com.example.btl_cnpm.ui.sign_up.SignUpViewModel
import com.example.btl_cnpm.utils.FoodEntity
import com.example.btl_cnpm.utils.UIState
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class SearchFragment : BaseFragment<FoodRecipeFragmentSearchBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_search

    private var filterTime: Int? = 0
    private var filterRate: Int? = 0
    private var listCategory = arrayListOf<Category>()
    private val searchViewModel by viewModels<SearchViewModel>()
    private val searchRecipeAdapter by lazy {
        SearchRecipeAdapter(onItemClick = {

        })
    }
    private val filterTimeAdapter by lazy {
        FilterAdapter(onItemClick = {
            filterTime = it
        })
    }

    private val filterRateAdapter by lazy {
        FilterAdapter(onItemClick = {
            filterRate = it
        })
    }

    private val categoryAdapter by lazy {
        CategoryAdapter(onItemClick = {

        })
    }

    override fun initView() {
        super.initView()
        binding.apply {
            headerSearch.tvHeader.text = binding.root.context.getString(R.string.search)
            headerSearch.btnBackHeader.setOnClickListener{
                requireView().findNavController().popBackStack()
            }
            headerSearch.btnMoreHeader.visibility = View.GONE
            rvSearchRecipe.adapter = searchRecipeAdapter
            txtResult.visibility = View.GONE
        }
    }

    override fun initAction() {
        super.initAction()
        binding.apply {
            listCategory.clear()

            btnFilter.setOnClickListener {
                filterDialog()
            }

            edtSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    p0?.let {
                        if (p0.isNotEmpty()) {
                            searchViewModel.getRecipes().observe(requireActivity()) { arr ->
                                when (arr) {
                                    is UIState.Success -> {
                                        val listRecipe = arr.data.filter { recipe ->
                                            recipe.name.contains(p0.toString(), true)
                                        }
                                        searchRecipeAdapter.submitList(listRecipe)
                                        txtResult.text = "${listRecipe.size} results"
                                        txtResult.visibility = View.VISIBLE
                                    }

                                    is UIState.Failure -> {
                                        showDialogFail("Please restart")
                                    }
                                }
                            }
                        }
                    }
                }

                override fun afterTextChanged(p0: Editable?) {

                }
            }
            )

            searchViewModel.getRecipes().observe(requireActivity()) {
                when (it) {
                    is UIState.Success -> {
                        searchRecipeAdapter.submitList(it.data)
                    }

                    is UIState.Failure -> {
                        showDialogFail("Please restart")
                    }
                }
            }

            searchViewModel.getCategory().observe(requireActivity()) {
                when (it) {
                    is UIState.Success -> {
                        listCategory = it.data
                    }

                    is UIState.Failure -> {
                        showDialogFail("Please restart")
                    }
                }
            }
        }
    }


    private fun filterDialog() {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.food_recipe_layout_filter, null)
        val rvTimeFilter = view.findViewById<RecyclerView>(R.id.rv_time_filter)
        val rvRateFilter = view.findViewById<RecyclerView>(R.id.rv_rate_filter)
        val rvCategory = view.findViewById<RecyclerView>(R.id.rv_category)
        val btnFilter = view.findViewById<AppCompatButton>(R.id.btn_filter)
        rvTimeFilter.adapter = filterTimeAdapter
        filterTimeAdapter.submitList(
            listOf<Int>(
                FoodEntity.ALL,
                FoodEntity.NEWEST,
                FoodEntity.OLDEST,
                FoodEntity.POPULARITY
            )
        )

        rvRateFilter.adapter = filterRateAdapter
        filterRateAdapter.submitList(
            listOf<Int>(
                FoodEntity.ONE_STAR,
                FoodEntity.TWO_STAR,
                FoodEntity.THREE_STAR,
                FoodEntity.FOUR_STAR,
                FoodEntity.FIVE_STAR
            )
        )

        btnFilter.setOnClickListener {

        }
        rvCategory.adapter = categoryAdapter
        categoryAdapter.submitList(listCategory)
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }
}