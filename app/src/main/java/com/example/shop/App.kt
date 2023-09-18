package com.example.shop

import android.app.Application
import android.content.SharedPreferences
import com.example.shop.feature.auth.AuthViewModel
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
import com.example.shop.feature.list.ListViewModel
import com.example.shop.services.data.repository.BannerRepository
import com.example.shop.services.data.repository.BannerRepositoryImpl
import com.example.shop.services.data.repository.CartRepository
import com.example.shop.services.data.repository.CartRepositoryImpl
import com.example.shop.services.data.repository.CommentRepository
import com.example.shop.services.data.repository.CommentRepositoryImpl
import com.example.shop.services.data.repository.UserRepository
import com.example.shop.services.data.repository.UserRepositoryImpl
import com.example.shop.services.data.source.UserDataSource
import com.example.shop.services.data.source.local.UserLocalDataSource
import com.example.shop.services.data.source.remote.BannerRemoteDataSource
import com.example.shop.services.data.source.remote.CartRemoteDataSource
import com.example.shop.services.data.source.remote.CommentRemoteDataSource
import com.example.shop.services.data.source.remote.UserRemoteDataSource
import com.example.shop.services.service.GlideImageLoadingServiceImpl
import com.example.shop.services.service.ImageLoadingService
import io.reactivex.rxjava3.core.Single
import org.koin.android.ext.android.get
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
            single<SharedPreferences> {
                this@App.getSharedPreferences(
                    "app_settings",
                    MODE_PRIVATE
                )
            }
            single<UserDataSource> { UserLocalDataSource(get()) }
            single<ApiService> { createApiServiceInstance(get()) }
            single<ImageLoadingService> { GlideImageLoadingServiceImpl() }

            single<UserRepository> {
                UserRepositoryImpl(UserRemoteDataSource(get()), get())
            }
            factory<ProductRepository> {
                ProductRepositoryImpl(
                    ProductRemoteDataSource(get()),
                    ProductLocalDataSource()
                )
            }
            factory { (viewType: Int) -> ProductListAdapter(viewType, get(), get()) }
            factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            factory<CommentRepository> { CommentRepositoryImpl(CommentRemoteDataSource(get())) }
            factory<CartRepository> { CartRepositoryImpl(CartRemoteDataSource(get())) }
            viewModel { HomeViewModel(get(), get()) }
            viewModel { DetailViewModel(get(), get(), get()) }
            viewModel { CommentListViewModel(get(), get()) }
            viewModel { (sort: Int) -> ListViewModel(sort, get()) }
            viewModel { DetailViewModel(get(), get(), get()) }
            viewModel { AuthViewModel(get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }

        //to Save token from sharedPreference in memory
        val userRepository: UserRepository = get()
        userRepository.loadToken()
    }
}