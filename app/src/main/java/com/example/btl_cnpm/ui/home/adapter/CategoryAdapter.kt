package com.example.btl_cnpm.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.btl_cnpm.databinding.FoodRecipeLayoutItemCategoryBinding
import com.example.btl_cnpm.model.Category

class CategoryAdapter(val onItemClick: (String) -> Unit) : ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(object: DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }


}) {
    inner class CategoryViewHolder(private val binding: FoodRecipeLayoutItemCategoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(category: Category) {
            binding.txtRecipeCategory.text = category.name
            itemView.setOnClickListener {
                onItemClick.invoke(category.id)
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