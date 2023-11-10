package com.example.btl_cnpm.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btl_cnpm.databinding.FoodRecipeLayoutItemProfileRecipeBinding
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.model.User

class ProfileRecipeAdapter(val onItemClick: (String) -> Unit): ListAdapter<Map.Entry<Recipe, User>, ProfileRecipeAdapter.ProfileRecipeViewHolder>(object: DiffUtil.ItemCallback<Map.Entry<Recipe, User>>() {
    override fun areItemsTheSame(
        oldItem: Map.Entry<Recipe, User>,
        newItem: Map.Entry<Recipe, User>
    ): Boolean {
        return oldItem.value.id == newItem.value.id && oldItem.key.id == newItem.key.id
    }

    override fun areContentsTheSame(
        oldItem: Map.Entry<Recipe, User>,
        newItem: Map.Entry<Recipe, User>
    ): Boolean {
        return oldItem.value.id == newItem.value.id && oldItem.key.id == newItem.key.id
    }


}) {
    inner class ProfileRecipeViewHolder(private val binding: FoodRecipeLayoutItemProfileRecipeBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(recipe: Map.Entry<Recipe, User>) {
            Glide.with(binding.root.context).load(recipe.key.image).into(binding.imgRecipe)
            binding.txtRecipeName.text = recipe.key.name
            binding.txtRecipeMinute.text = "${recipe.key.timer} mins"
            binding.txtCreatorName.text = "By ${recipe.value.username}"
            binding.txtRecipeRate.text = recipe.key.rate.toString()
            itemView.setOnClickListener {
                onItemClick.invoke(recipe.key.id)
            }
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