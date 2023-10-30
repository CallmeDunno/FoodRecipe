package com.example.btl_cnpm.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btl_cnpm.databinding.FoodRecipeLayoutItemProfileRecipeBinding
import com.example.btl_cnpm.model.Recipe

class ProfileRecipeAdapter(val onItemClick: (String) -> Unit): ListAdapter<Recipe, ProfileRecipeAdapter.ProfileRecipeViewHolder>(object: DiffUtil.ItemCallback<Recipe>() {
    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.id == newItem.id
    }

}) {
    inner class ProfileRecipeViewHolder(private val binding: FoodRecipeLayoutItemProfileRecipeBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(recipe: Recipe) {
            Glide.with(binding.root.context).load(recipe.image).into(binding.imgRecipe)
            binding.txtRecipeName.text = recipe.name
            binding.txtRecipeMinute.text = "${recipe.timer} mins"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileRecipeViewHolder {
        val inflater = FoodRecipeLayoutItemProfileRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProfileRecipeViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ProfileRecipeViewHolder, position: Int) {
        getItem(position)?.let {
            holder.onBind(it)
        }
    }
}