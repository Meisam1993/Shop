package com.example.shop.services.data.repository

import com.example.shop.services.data.dataclasses.Comment
import io.reactivex.rxjava3.core.Single


interface CommentRepository {

    fun getComments(productId: Int): Single<List<Comment>>

    fun insertComment(): Single<Comment>
}