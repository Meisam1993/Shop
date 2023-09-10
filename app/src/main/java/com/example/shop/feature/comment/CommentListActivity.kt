package com.example.shop.feature.comment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shop.base.BaseActivity
import com.example.shop.databinding.ActivityCommentListBinding
import com.example.shop.feature.detail.comment.CommentAdapter
import com.example.shop.services.data.dataclasses.Comment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommentListActivity : BaseActivity() {
    private val viewModel: CommentListViewModel by viewModel()
    private val commentListAdapter = CommentAdapter(true)
    private lateinit var binding: ActivityCommentListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        observe()
        initViews()
    }

    private fun initViews() {
        binding.back.setOnClickListener {
            finish()
        }
        binding.commentListRv.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.commentListRv.adapter = commentListAdapter
    }

    private fun observe() {
        viewModel.commentsLiveData.observe(this) {
            commentListAdapter.comments = it as ArrayList<Comment>
        }

        viewModel.progressBarLiveData.observe(this) {
            setProgressBarIndicator(it)
        }
    }
}