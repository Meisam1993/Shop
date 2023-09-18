package com.example.shop.feature.auth

import androidx.lifecycle.MutableLiveData
import com.example.shop.base.BaseViewModel
import com.example.shop.services.data.repository.UserRepository
import io.reactivex.rxjava3.core.Completable

class AuthViewModel(private val userRepositoryImpl: UserRepository) : BaseViewModel() {
    val progressBarLiveData = MutableLiveData<Boolean>()

    fun login(email: String, password: String): Completable {
        progressBarLiveData.value = true
        return userRepositoryImpl.login(email, password)
            .doFinally {
                progressBarLiveData.postValue(false)
            }
    }

    fun signUp(email: String, password: String): Completable {
        progressBarLiveData.value = true
        return userRepositoryImpl.signUp(email, password)
            .doFinally {
                progressBarLiveData.postValue(false)
            }
    }
}