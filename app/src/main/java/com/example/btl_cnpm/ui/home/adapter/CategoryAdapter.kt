package com.example.btl_cnpm.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.btl_cnpm.databinding.FoodRecipeLayoutItemCategoryBinding
import com.example.btl_cnpm.model.CategoryType

class CategoryAdapter(val onItemClick: (String) -> Unit) : ListAdapter<CategoryType, CategoryAdapter.CategoryViewHolder>(object: DiffUtil.ItemCallback<CategoryType>() {
    override fun areItemsTheSame(oldItem: CategoryType, newItem: CategoryType): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CategoryType, newItem: CategoryType): Boolean {
        return oldItem.id == newItem.id
    }


}) {
    inner class CategoryViewHolder(private val binding: FoodRecipeLayoutItemCategoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(categoryType: CategoryType) {
            binding.txtRecipeCategory.text = categoryType.name
            itemView.setOnClickListener {
                onItemClick.invoke(categoryType.id)
            }
        }

}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = FoodRecipeLayoutItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return CategoryViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }


}