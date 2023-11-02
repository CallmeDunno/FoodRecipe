package com.example.btl_cnpm.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.btl_cnpm.R
import lv.chi.photopicker.loader.ImageLoader

class GlideImageLoader : ImageLoader {
    override fun loadImage(context: Context, view: ImageView, uri: Uri) {
        Glide.with(context)
            .load(uri)
            .placeholder(R.drawable.bg_placeholder)
            .centerCrop()
            .into(view)
    }
}