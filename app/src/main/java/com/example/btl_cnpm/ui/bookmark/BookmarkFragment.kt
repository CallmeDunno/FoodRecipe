package com.example.btl_cnpm.ui.bookmark

import androidx.fragment.app.viewModels
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

    @Inject lateinit var sharedPre: SharedPreferencesManager

    private val adapter by lazy {
        BookmarkAdapter {
            notify(it)
        }
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

            viewModel.getAllItemBookmark(getIdUser()).observe(viewLifecycleOwner) {
                if (it.isEmpty()) tvNullItem.show() else tvNullItem.hide()
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
                //TODO("Check this if it have bug")
                val position = viewHolder.layoutPosition
                val list: ArrayList<BookmarkLocal> =
                    adapter.currentList as ArrayList<BookmarkLocal>
                val deletedBookmark = adapter.currentList[position]
                if (deletedBookmark != null) {
                    viewModel.deleteItemBookmark(deletedBookmark.id!!)
                    list.remove(deletedBookmark)
                    adapter.submitList(list)
                    adapter.notifyItemRemoved(position)
                }
                Snackbar.make(
                    binding.rcvBookmark,
                    "Deleted " + deletedBookmark.name,
                    Snackbar.LENGTH_LONG
                )
                    .setAction(
                        "Undo"
                    ) {
                        if (deletedBookmark != null) {
                            viewModel.insertBookmark(deletedBookmark)
                            list.add(deletedBookmark)
                            adapter.submitList(list)
                            adapter.notifyItemInserted(position)
                        }
                    }.show()
            }

        }).attachToRecyclerView(binding.rcvBookmark)
    }

    private fun getIdUser(): String {
        val idRemember = sharedPre.getString("idUserRemember")
        val idTemp = sharedPre.getString("idUserTemp")
        return if (idRemember.isNullOrEmpty()) idTemp!! else idRemember
    }

}