package com.example.shop.feature.home


import com.example.shop.services.data.dataclasses.Product
import com.example.shop.services.data.repository.ProductRepository
import com.example.shop.base.BaseSingleObserver
import com.example.shop.base.BaseViewModel
import com.example.shop.base.asyncNetworkRequest
import com.example.shop.services.data.dataclasses.Banner
import com.example.shop.services.data.dataclasses.SORT_LATEST
import com.example.shop.services.data.dataclasses.SORT_POPULAR
import com.example.shop.services.data.repository.BannerRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class HomeViewModel(
    private val bannerRepositoryImpl: BannerRepository,
    private val productRepositoryImpl: ProductRepository
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getBanners()
        getLatestProducts()
        getPopularProducts()
    }

    private fun getBanners() {
        bannerRepositoryImpl.getBanners()
            .asyncNetworkRequest()
            .subscribe(object : BaseSingleObserver<List<Banner>>(compositeDisposable) {
                override fun onSuccess(t: List<Banner>) {
                    Timber.i(t.toString())
                    _uiState.value = _uiState.value.copy(
                        banners = t,
                        progressBar = false
                    )
                }

                override fun onError(e: Throwable) {
                    _uiState.value = _uiState.value.copy(
                        errorResponse = e,
                        progressBar = false
                    )
                }
            })
    }

    private fun getLatestProducts() {
        productRepositoryImpl.getProducts(SORT_LATEST)
            .asyncNetworkRequest()
            .subscribe(object : BaseSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    _uiState.value = _uiState.value.copy(
                        products = t,
                        progressBar = false
                    )
                }

                override fun onError(e: Throwable) {
                    _uiState.value = _uiState.value.copy(
                        errorResponse = e,
                        progressBar = false
                    )
                }

            })
    }

    private fun getPopularProducts() {
        productRepositoryImpl.getProducts(SORT_POPULAR)
            .asyncNetworkRequest()
            .subscribe(object : BaseSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    _uiState.value = _uiState.value.copy(
                        products = t,
                        progressBar = false
                    )
                }

                override fun onError(e: Throwable) {
                    _uiState.value = _uiState.value.copy(
                        errorResponse = e,
                        progressBar = false
                    )
                }

            })
    }
}