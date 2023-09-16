package com.example.shop.base

import androidx.annotation.StringRes

class BaseException(
    val type: Type,
    @StringRes val userFriendlyMessage: Int = 0,
    val serverMessage: String? = null
) : Throwable() {

    enum class Type {
        SIMPLE, AUTH
    }
}