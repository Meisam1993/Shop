package com.example.shop.services.data.source.remote

import com.example.shop.services.data.dataclasses.Product
import com.example.shop.services.data.http.ApiService
import com.example.shop.services.data.source.ProductDataSource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class ProductRemoteDataSource(private val apiService: ApiService) : ProductDataSource {

    override fun getProducts(sort: Int): Single<List<Product>> =
        apiService.getProducts(sort.toString())

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