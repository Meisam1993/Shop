package com.example.shop.services.data.source

import com.example.shop.services.data.dataclasses.Product
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface ProductDataSource {

    fun getProducts(sort: Int): Single<List<Product>>

    fun getFavorites(): Single<List<Product>>

    fun addToFavorites(): Completable

    fun deleteFromFavorites(): Completable
}