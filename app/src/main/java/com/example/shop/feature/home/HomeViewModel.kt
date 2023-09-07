package com.example.shop.feature.home


import com.example.shoesshoppractice.services.data.repository.ProductRepository
import com.example.shop.base.BaseSingleObserver
import com.example.shop.base.BaseViewModel
import com.example.shop.services.data.dataclasses.Banner
import com.example.shop.services.data.repository.BannerRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.schedulers.Timed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class HomeViewModel(private val bannerRepositoryImpl: BannerRepository, private val productRepositoryImpl: ProductRepository) : BaseViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getBanners()
    }

    private fun getBanners() {
        bannerRepositoryImpl.getBanners()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: BaseSingleObserver<List<Banner>>(compositeDisposable) {
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
}