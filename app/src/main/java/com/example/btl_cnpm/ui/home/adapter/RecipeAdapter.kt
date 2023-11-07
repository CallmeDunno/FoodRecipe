package com.example.btl_cnpm.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btl_cnpm.databinding.FoodRecipeLayoutItemRecipeBinding
import com.example.btl_cnpm.model.Recipe

class RecipeAdapter(val onItemClick:(String) -> Unit): ListAdapter<Recipe, RecipeAdapter.RecipeViewHolder>(object : DiffUtil.ItemCallback<Recipe>() {
    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.id == newItem.id
    }

}){
    inner class RecipeViewHolder(private val binding: FoodRecipeLayoutItemRecipeBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(recipe: Recipe) {
            Glide.with(binding.root.context).load(recipe.image).into(binding.imgRecipe)
            binding.txtRecipeName.text = recipe.name
            binding.txtRecipeMinute.text = "${recipe.timer}"
            binding.txtRecipeRate.text = recipe.rate.toString()
            binding.btnMark.setOnClickListener {

            }
            itemView.setOnClickListener {
                onItemClick.invoke(recipe.id)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater = FoodRecipeLayoutItemRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecipeViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}