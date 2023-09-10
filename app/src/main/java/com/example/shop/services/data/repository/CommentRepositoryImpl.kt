package com.example.shop.services.data.repository

import com.example.shop.services.data.dataclasses.Comment
import com.example.shop.services.data.source.CommentDataSource
import io.reactivex.rxjava3.core.Single

class CommentRepositoryImpl(private val commentRemoteDataSource: CommentDataSource) :
    CommentRepository {

    override fun getComments(productId: Int): Single<List<Comment>> =
        commentRemoteDataSource.getComments(productId)

    override fun insertComment(): Single<Comment> {
        TODO("Not yet implemented")
    }
}