package com.example.shop.services.data.repository

import com.example.shop.services.data.TokenContainerEntity
import io.reactivex.rxjava3.core.Completable

interface UserRepository {

    fun login(username: String, password: String): Completable

    fun signUp(username: String, password: String): Completable

    fun loadToken(): TokenContainerEntity
}