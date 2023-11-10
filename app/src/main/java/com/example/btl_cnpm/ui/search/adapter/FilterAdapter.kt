package com.example.btl_cnpm.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.btl_cnpm.R
import com.example.btl_cnpm.databinding.FoodRecipeLayoutItemFilterBinding
import com.example.btl_cnpm.utils.DataLocal

class FilterAdapter(val onItemClick: (Int) -> Unit): ListAdapter<Int, FilterAdapter.FilterViewHolder>(object: DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }

}) {
    inner class FilterViewHolder(private val binding: FoodRecipeLayoutItemFilterBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Int) {
            if(item >= 10) {
                binding.imgStar.visibility = View.GONE
            }
            when(item) {
                DataLocal.ONE_STAR -> binding.txtFilter.text = binding.root.context.getString(R.string.one)
                DataLocal.TWO_STAR -> binding.txtFilter.text = binding.root.context.getString(R.string.two)
                DataLocal.THREE_STAR -> binding.txtFilter.text = binding.root.context.getString(R.string.three)
                DataLocal.FOUR_STAR -> binding.txtFilter.text = binding.root.context.getString(R.string.four)
                DataLocal.FIVE_STAR -> binding.txtFilter.text = binding.root.context.getString(R.string.five)
            }
            itemView.setOnClickListener {
                onItemClick.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val inflater = FoodRecipeLayoutItemFilterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FilterViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}