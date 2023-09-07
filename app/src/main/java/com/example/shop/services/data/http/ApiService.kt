package com.example.shop.services.data.http

import com.example.shoesshoppractice.services.data.dataclasses.Product
import com.example.shop.services.data.dataclasses.Banner
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "http://expertdevelopers.ir/api/v1/"

interface ApiService {

    @GET("product/list")
    fun getProducts(): Single<List<Product>>

    @GET("banner/slider")
    fun getBanners(): Single<List<Banner>>
}

fun createApiServiceInstance(): ApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(ApiService::class.java)
}