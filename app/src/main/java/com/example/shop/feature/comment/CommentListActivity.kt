package com.example.shop.feature.comment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shop.R
import com.example.shop.base.BaseActivity
import com.example.shop.databinding.ActivityCommentListBinding
import com.example.shop.databinding.ActivityDetailBinding

class CommentListActivity : BaseActivity() {
    private lateinit var binding: ActivityCommentListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}