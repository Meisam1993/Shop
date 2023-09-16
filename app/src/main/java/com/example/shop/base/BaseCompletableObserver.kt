package com.example.shop.base

import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

abstract class BaseCompletableObserver(private val compositeDisposable: CompositeDisposable) :
    CompletableObserver {

    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(d)
    }

    override fun onError(e: Throwable) {
        EventBus.getDefault().post(BaseExceptionMapper.map(e))
    }
}