package com.example.shop.services.data.http

import com.example.shop.services.data.dataclasses.TokenResponse
import com.example.shop.services.data.source.UserDataSource
import com.example.shop.services.data.source.remote.CLIENT_ID
import com.example.shop.services.data.source.remote.CLIENT_SECRET
import com.google.gson.JsonObject
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

class StoreAuthenticator() : Authenticator, KoinComponent {
    private val userLocalDataSource: UserDataSource by inject()
    private val apiService: ApiService by inject()

    override fun authenticate(route: Route?, response: Response): Request? {
        if (userLocalDataSource.loadTokenContainer().token != null &&
            userLocalDataSource.loadTokenContainer().refreshToken != null &&
            !response.request.url.pathSegments.last()
                .equals("token", false)
        ) {
            try {
                val token = refreshToken()
                if (token.isNullOrEmpty()) {
                    return null
                }
                return response.request.newBuilder()
                    .header("Authorization", "bearer $token")
                    .build()
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
        return null
    }

    private fun refreshToken(): String {
        val response: retrofit2.Response<TokenResponse> =
            apiService.refreshToken(JsonObject().apply {
                addProperty("grant_type", "refresh_token")
                addProperty("refresh_token", userLocalDataSource.loadTokenContainer().refreshToken)
                addProperty("client_id", CLIENT_ID)
                addProperty("client_secret", CLIENT_SECRET)
            }).execute()

        response.body()?.let {
            userLocalDataSource.saveToken(it.access_token, it.refresh_token)
            return it.access_token
        }
        return ""
    }
}