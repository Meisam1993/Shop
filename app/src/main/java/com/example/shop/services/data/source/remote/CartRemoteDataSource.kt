package com.example.shop.services.data.source.remote

import android.os.Message
import com.example.shop.services.data.dataclasses.MessageResponse
import com.example.shop.services.data.dataclasses.cart.AddToCartResponse
import com.example.shop.services.data.dataclasses.cart.CartItemCount
import com.example.shop.services.data.dataclasses.cart.CartListResponse
import com.example.shop.services.data.http.ApiService
import com.example.shop.services.data.source.CartDataSource
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Single
import org.json.JSONObject

class CartRemoteDataSource(private val apiService: ApiService) : CartDataSource {

    override fun addToCart(productId: Int): Single<AddToCartResponse> =
        apiService.addToCart(JsonObject().apply {
            addProperty("product_id", productId)
        })

    override fun getCartsList(): Single<CartListResponse> = apiService.getCartsList()

    override fun removeCartItem(cartItemId: Int): Single<MessageResponse> =
        apiService.removeCartItem(JsonObject().apply {
            addProperty("cart_item_id", cartItemId)
        })

    override fun changeCartItemCount(cartItemId: Int, count: Int): Single<AddToCartResponse> =
        apiService.changeCartItemCount(JsonObject().apply {
            addProperty("cart_item_id", cartItemId)
            addProperty("count", count)
        })

    override fun getCartItemsCount(): Single<CartItemCount> = apiService.getCartItemsCount()
}