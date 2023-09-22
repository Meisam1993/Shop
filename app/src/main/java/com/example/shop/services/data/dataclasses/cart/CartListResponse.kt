package com.example.shop.services.data.dataclasses.cart

data class CartListResponse(
    val cart_items: List<CartItem>,
    val payable_price: Int,
    val shipping_cost: Int,
    val total_price: Int
)

data class PurchaseDetails(
    var totalPrice: Int,
    var shippingCost: Int,
    var payablePrice: Int
)