package com.example.shop.feature.detail.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.shop.R
import com.example.shop.base.BaseSingleObserver
import com.example.shop.base.BaseViewModel
import com.example.shop.base.EXTRA_KEY
import com.example.shop.base.asyncNetworkRequest
import com.example.shop.services.data.dataclasses.Comment
import com.example.shop.services.data.dataclasses.EmptyState
import com.example.shop.services.data.dataclasses.Product
import com.example.shop.services.data.repository.CartRepository
import com.example.shop.services.data.repository.CommentRepository
import io.reactivex.rxjava3.core.Completable

class DetailViewModel(
    private val state: SavedStateHandle,
    private val commentRepositoryImpl: CommentRepository,
    private val cartRepositoryImpl: CartRepository
) : BaseViewModel() {
    val productLivedata: MutableLiveData<Product> =
        state.getLiveData<Product>(EXTRA_KEY)
    val commentsLiveData = MutableLiveData<List<Comment>>()
    val progressBarLivedata = MutableLiveData<Boolean>()
    val emptyStateLiveData = MutableLiveData<EmptyState>()

    init {
        getComment()
    }

    private fun getComment() {
        progressBarLivedata.value = true
        commentRepositoryImpl.getComments(productLivedata.value!!.id)
            .asyncNetworkRequest()
            .doFinally { progressBarLivedata.value = false }
            .subscribe(object : BaseSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t: List<Comment>) {
                    if (t.isNotEmpty()) {
                        commentsLiveData.value = t
                    } else {
                        emptyStateLiveData.value =
                            EmptyState(
                                true,
                                R.drawable.comment_empty_state,
                                R.string.comment_empty_state_message,
                                true,
                                R.string.insert_comment
                            )
                    }
                }
            })
    }

    fun onAddToCartBtnClick(): Completable {
        return cartRepositoryImpl.addToCart(productLivedata.value!!.id).ignoreElement()
    }
}