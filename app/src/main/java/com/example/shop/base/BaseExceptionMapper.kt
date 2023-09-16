package com.example.shop.base

import com.example.shop.R
import org.json.JSONObject
import retrofit2.HttpException

class BaseExceptionMapper {

    companion object {
        fun map(throwable: Throwable): BaseException {
            if (throwable is HttpException) {
                val errorJsonObject = JSONObject(throwable.response()?.errorBody()!!.string())
                val errorMessage = errorJsonObject.getString("message")
                return BaseException(
                    if (throwable.code() == 401) BaseException.Type.AUTH else BaseException.Type.SIMPLE,
                    serverMessage = errorMessage
                )
            }
            return BaseException(BaseException.Type.SIMPLE, R.string.unknown_error)
        }
    }
}