package com.example.shop.feature.home

import com.example.shop.services.data.dataclasses.Product
import com.example.shop.services.data.dataclasses.Banner

data class UiState(
    val banners: List<Banner> = listOf(),
    val products: List<Product> = listOf(),
    val errorResponse: Throwable? = null,
    val progressBar: Boolean = true
)
