package com.example.btl_cnpm.ui.search

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.data.local.RecipeLocal.RecipeLocal
import com.example.btl_cnpm.databinding.FoodRecipeFragmentSearchBinding
import com.example.btl_cnpm.model.CategoryType
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.model.User
import com.example.btl_cnpm.ui.home.adapter.CategoryAdapter
import com.example.btl_cnpm.ui.search.adapter.FilterAdapter
import com.example.btl_cnpm.ui.search.adapter.SearchRecipeAdapter
import com.example.btl_cnpm.utils.DataLocal
import com.example.btl_cnpm.utils.SharedPreferencesManager
import com.example.btl_cnpm.utils.UIState
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.floor

@AndroidEntryPoint
class SearchFragment : BaseFragment<FoodRecipeFragmentSearchBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_search
    private var filterRate: Int? = -1
    private var userRecipeMap = hashMapOf<Recipe, User>()
    private var filterCategory: String? = null
    private var categoryList = arrayListOf<CategoryType>()
    private var userList = arrayListOf<User>()
    private var recipeList = mutableListOf<Recipe>()
    @Inject
    lateinit var sharedPre: SharedPreferencesManager
    private var userId = ""
    private val searchViewModel by viewModels<SearchViewModel>()
    private val searchRecipeAdapter by lazy {
        SearchRecipeAdapter(onItemClick = {
            searchViewModel.insertRecipeLocal(RecipeLocal(idRecipe = it, idUser = userId))
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToRecipeFragment(
                    it
                )
            )
        })
    }

    override fun initView() {
        super.initView()
        binding.apply {
            headerSearch.tvHeader.text = binding.root.context.getString(R.string.search)
            headerSearch.btnBackHeader.visibility = View.GONE
            headerSearch.btnMoreHeader.visibility = View.GONE
            txtResult.visibility = View.GONE
        }
    }

    override fun initAction() {
        super.initAction()
        binding.apply {
            arguments?.let {
                edtSearch.setText(it.getString("edtSearch"))
            }
            sharedPre.getString("idUserRemember")?.let {
                userId = it
            }
            sharedPre.getString("idUserTemp")?.let {
                userId = it
            }
            rvSearchRecipe.adapter = searchRecipeAdapter
            recipeList.clear()
            userRecipeMap.clear()
            searchViewModel.getRecipes().observe(requireActivity()) {
                when (it) {
                    is UIState.Success -> {
                        recipeList = it.data
                        searchViewModel.getUsers().observe(requireActivity()) { result ->
                            when (result) {
                                is UIState.Success -> {
                                    userList = result.data
                                    runBlocking {
                                        launch {
                                            getUserList()
                                            val search = edtSearch.text
                                            if (search.isNullOrEmpty()) {
                                                searchViewModel.getSearchedRecipes(userId).observe(requireActivity()) {list ->
                                                    searchRecipeAdapter.submitList(
                                                        userRecipeMap.entries.filter { entry ->
                                                            list.contains(entry.key.id)
                                                        }.reversed()
                                                    )
                                                }
                                            } else {
                                                searchRecipeAdapter.submitList(userRecipeMap.entries.filter {entry->
                                                    entry.key.name.contains(search, true)
                                                })
                                            }
                                        }
                                    }

                                }

                                is UIState.Failure -> {
                                    result.message?.let { mes ->
                                        showDialogFail(mes)
                                    }
                                }
                            }
                        }
                    }

                    is UIState.Failure -> {
                        it.message?.let { mes ->
                            showDialogFail(mes)
                        }
                    }
                }

            }
            searchViewModel.getCategory().observe(requireActivity()) {
                when (it) {
                    is UIState.Success -> {
                        categoryList = it.data
                    }

                    is UIState.Failure -> {
                        showDialogFail(it.message.toString())
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
                        searchRecipeAdapter.submitList(
                            userRecipeMap.entries.filter { entry ->
                                entry.key.name.contains(it, true)
                            }
                        )
                    }
                }

                override fun afterTextChanged(p0: Editable?) {
                    p0?.let {
                        searchRecipeAdapter.submitList(
                            userRecipeMap.entries.filter { entry ->
                                entry.key.name.contains(it, true)
                            }
                        )
                    }
                }
            }
            )


        }
    }


    private fun filterDialog() {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.food_recipe_layout_filter, null)
        val rvRateFilter = view.findViewById<RecyclerView>(R.id.rv_rate_filter)
        val rvCategory = view.findViewById<RecyclerView>(R.id.rv_category)
        val btnFilter = view.findViewById<AppCompatButton>(R.id.btn_filter)
        val txtRate = view.findViewById<TextView>(R.id.txt_rate)
        val txtCategoryType = view.findViewById<TextView>(R.id.txt_category)
        val filterRateAdapter by lazy {
            FilterAdapter(onItemClick = {
                filterRate = it
                txtRate.text = "$it"
            })
        }
        val categoryAdapter by lazy {
            CategoryAdapter(onItemClick = {
                filterCategory = it.id
                txtCategoryType.text = it.name
            })
        }
        filterRate = -1
        filterCategory = ""
        txtRate.text = ""
        txtCategoryType.text = ""


        rvRateFilter.adapter = filterRateAdapter
        filterRateAdapter.submitList(
            listOf<Int>(
                DataLocal.ONE_STAR,
                DataLocal.TWO_STAR,
                DataLocal.THREE_STAR,
                DataLocal.FOUR_STAR,
                DataLocal.FIVE_STAR
            )
        )
        rvCategory.adapter = categoryAdapter
        categoryAdapter.submitList(categoryList)
        btnFilter.setOnClickListener {
            searchRecipeAdapter.submitList(filter().entries.toList())
            dialog.dismiss()
        }
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    suspend fun getUserList() {
        withContext(Dispatchers.Unconfined) {
            recipeList.forEach { recipe ->
                userList.forEach { user ->
                    if (recipe.idUser == user.id) {
                        userRecipeMap[recipe] = user
                    }
                }
            }
        }
    }
    fun filter(): HashMap<Recipe, User> {
        var filterMap = userRecipeMap
        if(filterRate != -1) {
            filterMap = filterMap.filter { entry -> floor(entry.key.rate).toInt() == filterRate } as HashMap<Recipe, User>
        }
        if(filterCategory != "") {
            filterMap = filterMap.filter {
                it.key.idCategoryType.contentEquals(filterCategory)
            } as HashMap<Recipe, User>
        }
        return filterMap
    }
}