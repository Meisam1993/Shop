package com.example.shop.services.data.source

import com.example.shop.services.data.dataclasses.Comment
import io.reactivex.rxjava3.core.Single

interface CommentDataSource {

    fun getComments(productId: Int): Single<List<Comment>>

    fun insertComment(): Single<Comment>
}