package com.example.shop.services.data.dataclasses.cart

import com.example.shop.services.data.dataclasses.Product

data class CartItem(
    val cart_item_id: Int,
    val count: Int,
    val product: Product
)