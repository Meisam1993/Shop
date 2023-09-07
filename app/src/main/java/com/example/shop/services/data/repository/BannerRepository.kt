package com.example.shop.services.data.repository

import com.example.shop.services.data.dataclasses.Banner
import io.reactivex.rxjava3.core.Single

interface BannerRepository {

    fun getBanners(): Single<List<Banner>>
}