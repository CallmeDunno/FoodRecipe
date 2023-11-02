package com.example.btl_cnpm

import android.app.Application
import com.example.btl_cnpm.utils.GlideImageLoader
import dagger.hilt.android.HiltAndroidApp
import lv.chi.photopicker.ChiliPhotoPicker

@HiltAndroidApp
class FoodRecipeApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        ChiliPhotoPicker.init(
            loader = GlideImageLoader(),
            authority = "lv.chi.sample.fileprovider"
        )
    }
}