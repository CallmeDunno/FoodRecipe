package com.example.btl_cnpm.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btl_cnpm.R
import com.example.btl_cnpm.databinding.FoodRecipeLayoutItemSearchRecipeBinding
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.model.User
import com.google.firebase.firestore.FirebaseFirestore

class SearchRecipeAdapter(val onItemClick: (String) -> Unit): ListAdapter<Map.Entry<Recipe, User>, SearchRecipeAdapter.SearchRecipeViewHolder>(object: DiffUtil.ItemCallback<Map.Entry<Recipe, User>>() {
    override fun areItemsTheSame(
        oldItem: Map.Entry<Recipe, User>,
        newItem: Map.Entry<Recipe, User>
    ): Boolean {
        return oldItem.value.id == newItem.value.id && oldItem.key.idUser == newItem.key.id && oldItem.key.id == newItem.key.id
    }

    override fun areContentsTheSame(
        oldItem: Map.Entry<Recipe, User>,
        newItem: Map.Entry<Recipe, User>
    ): Boolean {
        return oldItem.value.id == newItem.value.id && oldItem.key.idUser == newItem.key.id && oldItem.key.id == newItem.key.id
    }


}) {
    inner class SearchRecipeViewHolder(private val binding: FoodRecipeLayoutItemSearchRecipeBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(recipe: Map.Entry<Recipe, User>) {
            Glide.with(binding.root.context).load(recipe.key.image).into(binding.imgRecipe)
            binding.txtRecipeName.text = recipe.key.name
            binding.txtCreatorName.text = "By ${recipe.value.username}"
            binding.txtRecipeRate.text = recipe.key.rate.toString()
            itemView.setOnClickListener {
                onItemClick.invoke(recipe.key.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRecipeViewHolder {
        val inflater = FoodRecipeLayoutItemSearchRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchRecipeViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: SearchRecipeViewHolder, position: Int) {
        getItem(position)?.let {
            holder.onBind(it)
        }
    }
}