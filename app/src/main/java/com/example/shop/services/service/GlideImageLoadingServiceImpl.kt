package com.example.shop.services.service

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideImageLoadingServiceImpl : ImageLoadingService {

    override fun loadImage(context: Context, imageUrl: String, imageView: ImageView) {
        Glide.with(context)
            .load(imageUrl)
            .into(imageView)
    }
}