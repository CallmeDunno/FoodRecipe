package com.example.btl_cnpm.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.btl_cnpm.databinding.FoodRecipeLayoutNewRecipeBinding

class NewRecipeAdapter(val onItemCLick: (String)) {
    inner class NewRecipeViewHolder(private val binding: FoodRecipeLayoutNewRecipeBinding): RecyclerView.ViewHolder(binding.root) {

    }
}