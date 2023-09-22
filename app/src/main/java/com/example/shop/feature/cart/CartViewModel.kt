package com.example.shop.feature.cart

import androidx.lifecycle.MutableLiveData
import com.example.shop.base.BaseSingleObserver
import com.example.shop.base.BaseViewModel
import com.example.shop.base.asyncNetworkRequest
import com.example.shop.services.data.dataclasses.cart.CartItem
import com.example.shop.services.data.dataclasses.cart.CartItemCount
import com.example.shop.services.data.dataclasses.cart.CartListResponse
import com.example.shop.services.data.dataclasses.cart.PurchaseDetails
import com.example.shop.services.data.repository.CartRepository
import com.example.shop.services.data.source.UserDataSource
import io.reactivex.rxjava3.core.Completable

class CartViewModel(
    private val cartRepositoryImpl: CartRepository,
    private val userLocalDataSource: UserDataSource
) : BaseViewModel() {
    val cartItemsListLiveData = MutableLiveData<List<CartItem>>()
    val purchaseDetailsLiveData = MutableLiveData<PurchaseDetails>()
    val progressBarLiveData = MutableLiveData<Boolean>()

    private fun getCartResponse() {
        if (!userLocalDataSource.loadTokenContainer().token.isNullOrEmpty()) {
            progressBarLiveData.value = true
            cartRepositoryImpl.getCartsList()
                .asyncNetworkRequest()
                .doFinally { progressBarLiveData.value = false }
                .subscribe(object : BaseSingleObserver<CartListResponse>(compositeDisposable) {
                    override fun onSuccess(t: CartListResponse) {
                        if (t.cart_items.isNotEmpty()) {
                            cartItemsListLiveData.value = t.cart_items
                            purchaseDetailsLiveData.value =
                                PurchaseDetails(t.total_price, t.shipping_cost, t.payable_price)
                        }
                    }

                })
        }
    }

    fun getCartItemsCount() {

    }

    fun removeCartItem(cartItem: CartItem): Completable {
        return cartRepositoryImpl.removeCartItem(cartItem.cart_item_id)
            .doAfterSuccess { calculateAndPublishPurchaseDetails() }
            .ignoreElement()
    }

    fun increaseCartItemCount(cartItem: CartItem): Completable {
        return cartRepositoryImpl.changeCartItemCount(cartItem.cart_item_id, ++cartItem.count)
            .doAfterSuccess { calculateAndPublishPurchaseDetails() }
            .ignoreElement()
    }

    fun decreaseCartItemCount(cartItem: CartItem): Completable {
        return cartRepositoryImpl.changeCartItemCount(cartItem.cart_item_id, --cartItem.count)
            .doAfterSuccess { calculateAndPublishPurchaseDetails() }
            .ignoreElement()
    }

    fun refresh() {
        getCartResponse()
    }

    private fun calculateAndPublishPurchaseDetails() {
        cartItemsListLiveData.value?.let { cartItems ->
            purchaseDetailsLiveData.value?.let { purchaseDetails ->
                var totalPrice = 0
                var payablePrice = 0
                cartItems.forEach {
                    totalPrice += (it.product.price) * (it.count)
                    payablePrice += (it.product.price - it.product.discount) * (it.count)
                }

                purchaseDetails.totalPrice = totalPrice
                purchaseDetails.payablePrice = payablePrice
                purchaseDetailsLiveData.postValue(purchaseDetails)
            }
        }

    }
}