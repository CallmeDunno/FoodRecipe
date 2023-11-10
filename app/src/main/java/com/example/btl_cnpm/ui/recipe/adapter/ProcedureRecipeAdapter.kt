package com.example.btl_cnpm.ui.recipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.btl_cnpm.databinding.FoodRecipeItemStepBinding
import com.example.btl_cnpm.model.Procedure

class ProcedureRecipeAdapter :
    ListAdapter<Procedure, ProcedureRecipeAdapter.ProcedureRecipeVH>(AsyncDifferConfig.Builder(
        object : DiffUtil.ItemCallback<Procedure>() {
            override fun areItemsTheSame(oldItem: Procedure, newItem: Procedure): Boolean {
                return oldItem.index == newItem.index
            }

            override fun areContentsTheSame(oldItem: Procedure, newItem: Procedure): Boolean {
                return oldItem == newItem
            }
        }).build()) {

    inner class ProcedureRecipeVH(private val binding: FoodRecipeItemStepBinding) :
        ViewHolder(binding.root) {

        fun bindData(procedure: Procedure) {
            binding.item = procedure
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProcedureRecipeVH {
        return ProcedureRecipeVH(
            FoodRecipeItemStepBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProcedureRecipeVH, position: Int) {
        getItem(position)?.let { holder.bindData(getItem(position)) }
    }
}