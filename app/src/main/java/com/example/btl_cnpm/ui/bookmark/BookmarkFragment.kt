package com.example.btl_cnpm.ui.bookmark

import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentBookmarkBinding
import com.example.btl_cnpm.ui.bookmark.adapter.BookmarkAdapter
import com.example.btl_cnpm.utils.DataLocal
import com.example.btl_cnpm.utils.extensions.hide
import com.example.btl_cnpm.utils.extensions.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : BaseFragment<FoodRecipeFragmentBookmarkBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_bookmark

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
                tvHeader.text = "Saved recipes"
            }
            if (DataLocal.lBookmarks.isEmpty()) tvNullItem.show() else tvNullItem.hide()
            rcvBookmark.adapter = adapter
            adapter.submitList(DataLocal.lBookmarks)
        }
    }

    override fun initAction() {
        super.initAction()
    }
}