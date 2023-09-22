package com.example.shop.services.data.repository

import android.os.Message
import com.example.shop.base.convertDpToPixel
import com.example.shop.services.data.dataclasses.MessageResponse
import com.example.shop.services.data.dataclasses.cart.AddToCartResponse
import com.example.shop.services.data.dataclasses.cart.CartItemCount
import com.example.shop.services.data.dataclasses.cart.CartListResponse
import com.example.shop.services.data.source.CartDataSource
import io.reactivex.rxjava3.core.Single

class CartRepositoryImpl(private val cartRemoteDataSource: CartDataSource) : CartRepository {

    override fun addToCart(productId: Int): Single<AddToCartResponse> =
        cartRemoteDataSource.addToCart(productId)

    override fun getCartsList(): Single<CartListResponse> = cartRemoteDataSource.getCartsList()

    override fun removeCartItem(cartItemId: Int): Single<MessageResponse> =
        cartRemoteDataSource.removeCartItem(cartItemId)

    override fun changeCartItemCount(cartItemId: Int, count: Int): Single<AddToCartResponse> =
        cartRemoteDataSource.changeCartItemCount(cartItemId, count)

    override fun getCartItemsCount(): Single<CartItemCount> =
        cartRemoteDataSource.getCartItemsCount()
}