package com.example.shop.services.data.source.remote

import com.example.shop.services.data.TokenContainerEntity
import com.example.shop.services.data.dataclasses.MessageResponse
import com.example.shop.services.data.dataclasses.TokenResponse
import com.example.shop.services.data.http.ApiService
import com.example.shop.services.data.source.UserDataSource
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

const val CLIENT_ID = 2
const val CLIENT_SECRET = "kyj1c9sVcksqGU4scMX7nlDa1kjp2WoqQEf8PKAC"

class UserRemoteDataSource(private val apiService: ApiService) : UserDataSource {

    override fun login(username: String, password: String): Single<TokenResponse> =
        apiService.login(
            JsonObject().apply {
                addProperty("user_name", username)
                addProperty("password", password)
                addProperty("grant_type", "password")
                addProperty("client_id", CLIENT_ID)
                addProperty("client_secret", CLIENT_SECRET)
            }
        )

    override fun signUp(username: String, password: String): Single<MessageResponse> =
        apiService.signUp(JsonObject().apply {
            addProperty("email", username)
            addProperty("password", password)
        })

    override fun loadTokenContainer(): TokenContainerEntity {
        TODO("Not yet implemented")
    }

    override fun saveToken(token: String, refreshToken: String) {
        TODO("Not yet implemented")
    }
}