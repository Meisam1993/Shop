package com.example.shop.services.data.repository

import com.example.shop.services.data.dataclasses.TokenResponse
import com.example.shop.services.data.source.UserDataSource
import io.reactivex.rxjava3.core.Completable

class UserRepositoryImpl(
    private val userRemoteDataSource: UserDataSource,
    private val userLocalDataSource: UserDataSource
) : UserRepository {

    override fun login(username: String, password: String): Completable =
        userRemoteDataSource.login(username, password).doOnSuccess {
            onSuccessFulLogin(it)
        }.ignoreElement()

    override fun signUp(username: String, password: String): Completable =
        userRemoteDataSource.signUp(username, password).flatMap {
            userRemoteDataSource.login(username, password).doOnSuccess {
                onSuccessFulLogin(it)
            }
        }.ignoreElement()

    override fun loadToken() = userLocalDataSource.loadTokenContainer()

    private fun onSuccessFulLogin(tokenResponse: TokenResponse) {
        userLocalDataSource.saveToken(tokenResponse.access_token, tokenResponse.refresh_token)
    }
}