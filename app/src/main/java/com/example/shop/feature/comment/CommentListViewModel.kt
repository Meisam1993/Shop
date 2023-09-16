package com.example.shop.feature.comment

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

class CommentListViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val commentRepositoryImpl: CommentRepository
) : BaseViewModel() {
    private val productLivedata: MutableLiveData<Product> =
        savedStateHandle.getLiveData<Product>(EXTRA_KEY)
    val commentsLiveData = MutableLiveData<List<Comment>>()
    val progressBarLiveData = MutableLiveData<Boolean>()

    init {
        getComments()
    }

    private fun getComments() {
        progressBarLiveData.value = true
        productLivedata.value?.let {
            commentRepositoryImpl.getComments(it.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { progressBarLiveData.value = false }
                .subscribe(object : BaseSingleObserver<List<Comment>>(compositeDisposable) {
                    override fun onSuccess(t: List<Comment>) {
                        commentsLiveData.value = t
                    }
                })
        }
    }
}