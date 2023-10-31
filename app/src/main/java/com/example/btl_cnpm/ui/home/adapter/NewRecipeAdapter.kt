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

class NewRecipeAdapter(val onItemCLick: (String) -> Unit): ListAdapter<Recipe, NewRecipeAdapter.NewRecipeViewHolder>(object : DiffUtil.ItemCallback<Recipe>() {
    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.id == newItem.id
    }

}) {
    inner class NewRecipeViewHolder(private val binding: FoodRecipeLayoutNewRecipeBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(recipe: Recipe) {
            val context = binding.root.context
            val firestore = FirebaseFirestore.getInstance()
            Glide.with(context).load(recipe.image).into(binding.imgRecipe)
            binding.txtRecipeName.text = recipe.name
            binding.txtRecipeMinute.text = "${recipe.timer} mins"
            if(recipe.idUser.isNotEmpty()) {
                firestore.collection("User")
                    .document(recipe.idUser)
                    .get()
                    .addOnCompleteListener {
                        if(it.isSuccessful) {
                            if(it.result != null) {
                                binding.txtCreatorName.text =  "By ${it.result.toObject(User::class.java)!!.username}"
                            }
                        } else {
                            binding.txtCreatorName.text = context.getString(R.string.by_unknown)
                        }
                    }
            } else {
                binding.txtCreatorName.text = context.getString(R.string.by_unknown)
            }

            itemView.setOnClickListener {
                onItemCLick.invoke(recipe.id)
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