package com.example.shop.feature.list

import androidx.lifecycle.MutableLiveData
import com.example.shop.R
import com.example.shop.base.BaseSingleObserver
import com.example.shop.base.BaseViewModel
import com.example.shop.base.asyncNetworkRequest
import com.example.shop.services.data.dataclasses.Product
import com.example.shop.services.data.repository.ProductRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class ListViewModel(var sort: Int, private val productRepositoryImpl: ProductRepository) :
    BaseViewModel() {
    val productsLiveData = MutableLiveData<List<Product>>()
    val progressBarLiveData = MutableLiveData<Boolean>()
    val selectedSortsLiveData = MutableLiveData<Int>()
    var sortTitles = arrayOf(
        R.string.sort_latest,
        R.string.sort_popular,
        R.string.sort_price_desc,
        R.string.sort_price_asc
    )

    init {
        getProducts()
        selectedSortsLiveData.value = sortTitles[sort]
    }

    private fun getProducts() {
        progressBarLiveData.value = true
        productRepositoryImpl.getProducts(sort)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : BaseSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productsLiveData.value = t
                }
            })
    }

    fun onSortTypeChangerSelectByUser(sort: Int) {
        this.sort = sort
        this.selectedSortsLiveData.value = sortTitles[sort]
        getProducts()
    }
}