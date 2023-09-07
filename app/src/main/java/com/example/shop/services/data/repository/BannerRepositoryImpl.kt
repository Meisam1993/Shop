package com.example.shop.services.data.repository

import com.example.shop.services.data.dataclasses.Banner
import com.example.shop.services.data.source.BannerDateSource
import io.reactivex.rxjava3.core.Single

class BannerRepositoryImpl(private val bannerRemoteDataSource: BannerDateSource) : BannerRepository {

    override fun getBanners(): Single<List<Banner>> = bannerRemoteDataSource.getBanners()
}