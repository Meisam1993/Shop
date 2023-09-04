package com.example.shop

import android.app.Application
import com.example.shoesshoppractice.services.data.http.ApiService
import com.example.shoesshoppractice.services.data.http.createApiServiceInstance
import com.example.shoesshoppractice.services.data.repository.ProductRepository
import com.example.shoesshoppractice.services.data.repository.ProductRepositoryImpl
import com.example.shoesshoppractice.services.data.source.local.ProductLocalDataSource
import com.example.shoesshoppractice.services.data.source.remote.ProductRemoteDataSource
import com.example.shop.feature.home.HomeViewModel
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
            factory<ProductRepository> { ProductRepositoryImpl(ProductRemoteDataSource(get()), ProductLocalDataSource()) }
            viewModel { HomeViewModel(get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }
    }
}