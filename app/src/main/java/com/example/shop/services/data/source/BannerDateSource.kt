package com.example.shop.services.data.source

import com.example.shop.services.data.dataclasses.Banner
import io.reactivex.rxjava3.core.Single

interface BannerDateSource {

    fun getBanners(): Single<List<Banner>>
}