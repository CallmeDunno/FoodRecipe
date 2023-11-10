package com.example.btl_cnpm.ui.bookmark

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.data.local.BookmarkLocal
import com.example.btl_cnpm.databinding.FoodRecipeFragmentBookmarkBinding
import com.example.btl_cnpm.ui.bookmark.adapter.BookmarkAdapter
import com.example.btl_cnpm.utils.SharedPreferencesManager
import com.example.btl_cnpm.utils.extensions.hide
import com.example.btl_cnpm.utils.extensions.show
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BookmarkFragment : BaseFragment<FoodRecipeFragmentBookmarkBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_bookmark

    private val viewModel by viewModels<BookmarkViewModel>()
    private lateinit var idUser: String
    private lateinit var listBookmark: List<BookmarkLocal>

    @Inject
    lateinit var sharedPre: SharedPreferencesManager

    private val adapter by lazy {
        BookmarkAdapter {
            findNavController().navigate(
                BookmarkFragmentDirections.actionBookmarkFragmentToRecipeFragment(
                    it
                )
            )
        }
    }

    override fun initVariable() {
        super.initVariable()
        listBookmark = ArrayList()
        idUser = if (!sharedPre.getString("idUserTemp").isNullOrEmpty())
            sharedPre.getString("idUserTemp")!!
        else sharedPre.getString("idUserRemember")!!
    }

    override fun initView() {
        super.initView()
        binding.apply {
            headerBookmark.apply {
                btnBackHeader.hide()
                btnMoreHeader.hide()
                tvHeader.text = "Bookmark"
            }
            rcvBookmark.adapter = adapter

            viewModel.getAllItemBookmark(idUser).observe(viewLifecycleOwner) {
                if (it.isEmpty()) tvNullItem.show()
                else {
                    listBookmark = it
                    tvNullItem.hide()
                }
                adapter.submitList(it)
            }
        }
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(0, ItemTouchHelper.LEFT)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.layoutPosition
                val deletedBookmark = listBookmark[position]
                viewModel.deleteItemBookmark(deletedBookmark)
                Snackbar.make(
                    binding.rcvBookmark,
                    "Delete ${deletedBookmark.name} successful!",
                    Snackbar.LENGTH_LONG
                )
                    .setAction(
                        "Undo"
                    ) {
                        viewModel.insertBookmark(deletedBookmark)
                    }.show()
            }

        }).attachToRecyclerView(binding.rcvBookmark)
    }

}