package com.example.shop.services.service

import android.content.Context
import android.widget.ImageView

interface ImageLoadingService {

    fun loadImage(context: Context, imageUrl: String, imageView: ImageView)
}