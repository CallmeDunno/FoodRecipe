package com.example.btl_cnpm.ui.bookmark.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.btl_cnpm.databinding.FoodRecipeItemBookmarkBinding
import com.example.btl_cnpm.model.Bookmark

class BookmarkAdapter(private val onClick: (String) -> Unit) :
    ListAdapter<Bookmark, BookmarkAdapter.BookmarkVH>(AsyncDifferConfig.Builder(object :
        DiffUtil.ItemCallback<Bookmark>() {
        override fun areItemsTheSame(oldItem: Bookmark, newItem: Bookmark): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Bookmark, newItem: Bookmark): Boolean {
            return oldItem == newItem
        }
    }).build()) {

    inner class BookmarkVH(private val binding: FoodRecipeItemBookmarkBinding) :
        ViewHolder(binding.root) {

        fun bindData(bookmark: Bookmark) {
            itemView.setOnClickListener {
                onClick.invoke(bookmark.id)
            }
            binding.item = bookmark
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkAdapter.BookmarkVH {
        return BookmarkVH(
            FoodRecipeItemBookmarkBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BookmarkVH, position: Int) {
        getItem(position)?.let { holder.bindData(it) }
    }

}