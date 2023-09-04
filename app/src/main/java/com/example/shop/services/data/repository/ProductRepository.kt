package com.example.shoesshoppractice.services.data.repository

import com.example.shoesshoppractice.services.data.dataclasses.Product
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface ProductRepository {

    fun getProducts(): Single<List<Product>>

    fun getFavorites(): Single<List<Product>>

    fun addToFavorites(): Completable

    fun deleteFromFavorites(): Completable
}