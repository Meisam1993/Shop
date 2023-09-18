package com.example.shop.services.data.source.local

import android.content.SharedPreferences
import com.example.shop.services.data.TokenContainerEntity
import com.example.shop.services.data.dataclasses.MessageResponse
import com.example.shop.services.data.dataclasses.TokenResponse
import com.example.shop.services.data.source.UserDataSource
import io.reactivex.rxjava3.core.Single

class UserLocalDataSource(private val sharedPreferences: SharedPreferences) : UserDataSource {

    override fun login(username: String, password: String): Single<TokenResponse> {
        TODO("Not yet implemented")
    }

    override fun signUp(username: String, password: String): Single<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun loadTokenContainer() = TokenContainerEntity(
        token = sharedPreferences.getString("access_token", null),
        refreshToken = sharedPreferences.getString("refresh_token", null)
    )

    override fun saveToken(token: String, refreshToken: String) = sharedPreferences.edit().apply {
        putString("access_token", token)
        putString("refresh_token", refreshToken)
    }.apply()
}