package com.example.shop.services.data.source.remote

import com.example.shop.services.data.dataclasses.Comment
import com.example.shop.services.data.http.ApiService
import com.example.shop.services.data.source.CommentDataSource
import io.reactivex.rxjava3.core.Single

class CommentRemoteDataSource(private val apiService: ApiService) : CommentDataSource {

    override fun getComments(productId: Int): Single<List<Comment>> =
        apiService.getComments(productId)

    override fun insertComment(): Single<Comment> {
        TODO("Not yet implemented")
    }
}