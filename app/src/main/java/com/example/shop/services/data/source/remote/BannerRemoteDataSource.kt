package com.example.shop.services.data.source.remote

import com.example.shop.services.data.http.ApiService
import com.example.shop.services.data.dataclasses.Banner
import com.example.shop.services.data.source.BannerDateSource
import io.reactivex.rxjava3.core.Single

class BannerRemoteDataSource(private val apiService: ApiService) : BannerDateSource {

    override fun getBanners(): Single<List<Banner>> = apiService.getBanners()
}