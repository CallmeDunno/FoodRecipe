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

class SearchRecipeAdapter(val onItemClick: (String) -> Unit): ListAdapter<Recipe, SearchRecipeAdapter.SearchRecipeViewHolder>(object: DiffUtil.ItemCallback<Recipe>() {
    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.id == newItem.id
    }

}) {
    inner class SearchRecipeViewHolder(private val binding: FoodRecipeLayoutItemSearchRecipeBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(recipe: Recipe) {
            val context = binding.root.context
            val fFirestore = FirebaseFirestore.getInstance()
            Glide.with(binding.root.context).load(recipe.image).into(binding.imgRecipe)
            binding.txtRecipeName.text = recipe.name
            if(recipe.idUser.isNotEmpty()) {
                fFirestore.collection("User")
                    .document(recipe.idUser)
                    .get()
                    .addOnCompleteListener {
                        if(it.isSuccessful) {
                            if(it.result != null) {
                                binding.txtCreatorName.text = "By ${it.result.toObject(User::class.java)!!.username}"
                            }
                        } else {
                            binding.txtCreatorName.text = context.getString(R.string.by_unknown)
                        }
                    }
            } else {
                binding.txtCreatorName.text = context.getString(R.string.by_unknown)
            }

            itemView.setOnClickListener {
                onItemClick.invoke(recipe.id)
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