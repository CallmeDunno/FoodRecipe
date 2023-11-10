package com.example.btl_cnpm.utils.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("set_image_by_url")
fun setImage(imageView: ImageView, link: String?) {
    link?.let { Glide.with(imageView.context).load(link).into(imageView) }
}