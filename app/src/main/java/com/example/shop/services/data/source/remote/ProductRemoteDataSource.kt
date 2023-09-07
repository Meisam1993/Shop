package com.example.shoesshoppractice.services.data.source.remote

import com.example.shoesshoppractice.services.data.dataclasses.Product
import com.example.shop.services.data.http.ApiService
import com.example.shoesshoppractice.services.data.source.ProductDataSource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class ProductRemoteDataSource(private val apiService: ApiService) : ProductDataSource {
    override fun getProducts(): Single<List<Product>> = apiService.getProducts()

    override fun getFavorites(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun addToFavorites(): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorites(): Completable {
        TODO("Not yet implemented")
    }
}