package com.example.btl_cnpm.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btl_cnpm.R
import com.example.btl_cnpm.databinding.FoodRecipeLayoutNewRecipeBinding
import com.example.btl_cnpm.model.Recipe
import com.example.btl_cnpm.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class NewRecipeAdapter(val onItemCLick: (String) -> Unit): ListAdapter<Map.Entry<Recipe, User>, NewRecipeAdapter.NewRecipeViewHolder>(object : DiffUtil.ItemCallback<Map.Entry<Recipe, User>>() {
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
    inner class NewRecipeViewHolder(private val binding: FoodRecipeLayoutNewRecipeBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(recipe: Map.Entry<Recipe, User>) {
            val context = binding.root.context
            Glide.with(context).load(recipe.key.image).into(binding.imgRecipe)
            binding.txtRecipeName.text = recipe.key.name
            binding.txtRecipeMinute.text = "${recipe.key.timer} mins"
            binding.txtCreatorName.text = recipe.value.username
            if(recipe.value.image.isNotEmpty()) {
                Glide.with(context).load(recipe.value.image).into(binding.imgCreatorAvatar)
            }
            itemView.setOnClickListener {
                onItemCLick.invoke(recipe.key.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewRecipeViewHolder {
        val inflater = FoodRecipeLayoutNewRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewRecipeViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: NewRecipeViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

}