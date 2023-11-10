package com.example.btl_cnpm.ui.new_recipe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.btl_cnpm.R
import com.example.btl_cnpm.model.CategoryType

class SpinnerAdapter(context: Context, categoryTypeList: List<CategoryType>) :
    ArrayAdapter<CategoryType>(context, 0, categoryTypeList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertedView = convertView
        if (convertedView == null) {
            convertedView = LayoutInflater.from(parent.context)
                .inflate(R.layout.food_recipe_item_spinner, parent, false)
        }
        val textViewName: TextView = convertedView!!.findViewById(R.id.tvNameSpinner)
        getItem(position)?.let { textViewName.text = it.name }
        return convertedView
    }
}