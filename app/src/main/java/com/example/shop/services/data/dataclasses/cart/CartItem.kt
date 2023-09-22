package com.example.shop.services.data.dataclasses.cart

import com.example.shop.services.data.dataclasses.Product

data class CartItem(
    val cart_item_id: Int,
    var count: Int,
    val product: Product,
    var changeCountProgressBarVisibility: Boolean = false
)