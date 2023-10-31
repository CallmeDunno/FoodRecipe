package com.example.btl_cnpm.ui.bookmark.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.btl_cnpm.data.local.BookmarkLocal
import com.example.btl_cnpm.databinding.FoodRecipeItemBookmarkBinding

class BookmarkAdapter(private val onClick: (String) -> Unit) :
    ListAdapter<BookmarkLocal, BookmarkAdapter.BookmarkVH>(AsyncDifferConfig.Builder(object :
        DiffUtil.ItemCallback<BookmarkLocal>() {
        override fun areItemsTheSame(oldItem: BookmarkLocal, newItem: BookmarkLocal): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BookmarkLocal, newItem: BookmarkLocal): Boolean {
            return oldItem == newItem
        }
    }).build()) {

    inner class BookmarkVH(private val binding: FoodRecipeItemBookmarkBinding) :
        ViewHolder(binding.root) {

        fun bindData(bookmarkLocal: BookmarkLocal) {
            itemView.setOnClickListener {
                onClick.invoke(bookmarkLocal.idRecipe)
            }
            binding.item = bookmarkLocal
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