package com.example.shop.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoesshoppractice.services.data.dataclasses.Product
import com.example.shoesshoppractice.services.data.repository.ProductRepository
import com.example.shop.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class HomeViewModel(private val productRepositoryImpl: ProductRepository) : BaseViewModel() {
    private val _productLivedata = MutableLiveData<List<Product>>()
    val productLivedata: LiveData<List<Product>>
        get() = _productLivedata
    private val _progressBarLiveData = MutableLiveData<Boolean>()
    val progressBarLiveData: LiveData<Boolean>
        get() = _progressBarLiveData

    init {
        _progressBarLiveData.value = true
        productRepositoryImpl.getProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { _progressBarLiveData.value = false }
            .subscribe(object : SingleObserver<List<Product>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                }

                override fun onSuccess(t: List<Product>) {
                    _productLivedata.value = t
                }
            })
    }
}