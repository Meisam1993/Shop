package com.example.shoesshoppractice.services.data.repository

import com.example.shoesshoppractice.services.data.dataclasses.Product
import com.example.shoesshoppractice.services.data.source.ProductDataSource

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class ProductRepositoryImpl(
    private val productRemoteDataSource: ProductDataSource,
    private val productLocalDataSource: ProductDataSource
) : ProductRepository {
    override fun getProducts(): Single<List<Product>> = productRemoteDataSource.getProducts()

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