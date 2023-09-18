package com.example.shop.services.data.source

import com.example.shop.services.data.TokenContainerEntity
import com.example.shop.services.data.dataclasses.MessageResponse
import com.example.shop.services.data.dataclasses.TokenResponse
import io.reactivex.rxjava3.core.Single

interface UserDataSource {

    fun login(username: String, password: String): Single<TokenResponse>

    fun signUp(username: String, password: String): Single<MessageResponse>

    fun loadTokenContainer() : TokenContainerEntity

    fun saveToken(token:String, refreshToken: String)
}