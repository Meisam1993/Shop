package com.example.shop.services.data.dataclasses.cart

data class AddToCartResponse(
    val count: Int,
    val id: Int,
    val product_id: Int
)