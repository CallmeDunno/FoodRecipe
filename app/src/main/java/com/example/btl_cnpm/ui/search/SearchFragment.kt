package com.example.btl_cnpm.ui.search

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentSearchBinding
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.model.User
import com.example.btl_cnpm.ui.home.adapter.CategoryAdapter
import com.example.btl_cnpm.ui.search.adapter.FilterAdapter
import com.example.btl_cnpm.ui.search.adapter.SearchRecipeAdapter
import com.example.btl_cnpm.utils.FoodEntity
import com.example.btl_cnpm.utils.UIState
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FoodRecipeFragmentSearchBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_search

    private var filterTime: Int? = 0
    private var filterRate: Int? = 0
    private var userRecipeMap = hashMapOf<Recipe, User>()
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
            headerSearch.btnBackHeader.setOnClickListener {
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
            arguments?.let {
                edtSearch.setText(it.getString("edtSearch"))
            }

            searchViewModel.getRecipeByName().observe(requireActivity()) { result ->
                when (result) {
                    is UIState.Success -> {
                        userRecipeMap = result.data
                        edtSearch.text?.let {
                            if (it.isNotEmpty()) {
                                searchRecipeAdapter.submitList(userRecipeMap.entries.filter { entry ->
                                    entry.key.name.contains(it.toString(), true)
                                })
                            } else {
                                searchRecipeAdapter.submitList(userRecipeMap.entries.toList())
                            }
                        }
                    }

                    is UIState.Failure -> {
                        showDialogFail(result.message.toString())
                    }
                }
            }

            btnFilter.setOnClickListener {
                filterDialog()
            }

            edtSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    p0?.let {
                        searchRecipeAdapter.submitList(userRecipeMap.entries.filter { entry ->
                            entry.key.name.contains(it.toString(), true)
                        })
                    }
                }

                override fun afterTextChanged(p0: Editable?) {
                }
            }
            )


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
        rvCategory.adapter = categoryAdapter
        searchViewModel.getCategory().observe(requireActivity()) {
            when (it) {
                is UIState.Success -> {
                    categoryAdapter.submitList(it.data)
                }

                is UIState.Failure -> {
                    showDialogFail(it.message.toString())
                }
            }
        }
        btnFilter.setOnClickListener {

        }
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }
}