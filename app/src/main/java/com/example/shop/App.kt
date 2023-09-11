package com.example.shop

import android.app.Application
import com.example.shop.feature.comment.CommentListViewModel
import com.example.shop.feature.detail.product.DetailViewModel
import com.example.shop.services.data.http.ApiService
import com.example.shop.services.data.http.createApiServiceInstance
import com.example.shop.services.data.repository.ProductRepository
import com.example.shop.services.data.repository.ProductRepositoryImpl
import com.example.shop.services.data.source.local.ProductLocalDataSource
import com.example.shop.services.data.source.remote.ProductRemoteDataSource
import com.example.shop.feature.home.HomeViewModel
import com.example.shop.feature.common.ProductListAdapter
import com.example.shop.services.data.repository.BannerRepository
import com.example.shop.services.data.repository.BannerRepositoryImpl
import com.example.shop.services.data.repository.CommentRepository
import com.example.shop.services.data.repository.CommentRepositoryImpl
import com.example.shop.services.data.source.remote.BannerRemoteDataSource
import com.example.shop.services.data.source.remote.CommentRemoteDataSource
import com.example.shop.services.service.GlideImageLoadingServiceImpl
import com.example.shop.services.service.ImageLoadingService
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        val myModules = module {
            single<ApiService> { createApiServiceInstance() }
            single<ImageLoadingService> { GlideImageLoadingServiceImpl() }
            factory<ProductRepository> {
                ProductRepositoryImpl(
                    ProductRemoteDataSource(get()),
                    ProductLocalDataSource()
                )
            }
            factory { ProductListAdapter(get(), get()) }
            factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            factory<CommentRepository> { CommentRepositoryImpl(CommentRemoteDataSource(get())) }
            viewModel { HomeViewModel(get(), get()) }
            viewModel { DetailViewModel(get(), get()) }
            viewModel { CommentListViewModel(get(), get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }
    }
}