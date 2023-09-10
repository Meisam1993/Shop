package com.example.shop.feature.detail.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.shop.base.BaseSingleObserver
import com.example.shop.base.BaseViewModel
import com.example.shop.base.EXTRA_KEY
import com.example.shop.services.data.dataclasses.Comment
import com.example.shop.services.data.dataclasses.Product

import com.example.shop.services.data.repository.CommentRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class DetailViewModel(
    private val state: SavedStateHandle,
    private val commentRepositoryImpl: CommentRepository
) : BaseViewModel() {
    val productLivedata: MutableLiveData<Product> =
        state.getLiveData<Product>(EXTRA_KEY)
    val commentsLiveData = MutableLiveData<List<Comment>>()
    val progressBarLivedata = MutableLiveData<Boolean>()

    init {
        getComment()
    }

    private fun getComment() {
        progressBarLivedata.value = true
        commentRepositoryImpl.getComments(productLivedata.value!!.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progressBarLivedata.value = false }
            .subscribe(object : BaseSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t: List<Comment>) {
                    commentsLiveData.value = t
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                }

            })
    }
}