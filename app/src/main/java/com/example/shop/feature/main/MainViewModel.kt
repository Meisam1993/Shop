package com.example.shop.feature.main

import com.example.shop.base.BaseSingleObserver
import com.example.shop.base.BaseViewModel
import com.example.shop.services.data.dataclasses.cart.CartItemCount
import com.example.shop.services.data.repository.CartRepository
import com.example.shop.services.data.source.UserDataSource
import io.reactivex.rxjava3.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class MainViewModel(
    private val cartRepositoryImpl: CartRepository,
    private val userLocalDataSource: UserDataSource
) : BaseViewModel() {

    fun getCartItemsCount() {
//        if (!userLocalDataSource.loadTokenContainer().token.isNullOrEmpty()) {
//            cartRepositoryImpl.getCartsList()
//                .subscribeOn(Schedulers.io())
//                .subscribe(object : BaseSingleObserver<CartItemCount>(compositeDisposable) {
//                    override fun onSuccess(t: CartItemCount) {
//                        EventBus.getDefault().postSticky(t)
//                    }
//
//                })
//        }
    }
}