package com.example.shop.feature.detail.product

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shop.base.BaseActivity
import com.example.shop.base.EXTRA_KEY
import com.example.shop.base.formatDiscount
import com.example.shop.base.formatPrice
import com.example.shop.databinding.ActivityDetailBinding
import com.example.shop.feature.comment.CommentListActivity
import com.example.shop.feature.detail.comment.CommentAdapter
import com.example.shop.services.data.dataclasses.Comment
import com.example.shop.services.service.ImageLoadingService
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class DetailActivity : BaseActivity() {
    private val viewModel: DetailViewModel by viewModel()
    private val imageLoadingService: ImageLoadingService by inject()
    private val commentAdapter = CommentAdapter()
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        observe()
        initViews()

    }

    private fun initViews() {
        binding.detailToolbar.backBtn.setOnClickListener {
            finish()
        }
        binding.commentRv.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.commentRv.adapter = commentAdapter

    }

    private fun observe() {
        viewModel.productLivedata.observe(this) {
            imageLoadingService.loadImage(this@DetailActivity, it.image, binding.image)
            binding.title.text = it.title
            binding.previousPrice.text = formatPrice(it.previous_price)
            binding.previousPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.currentPrice.text = formatPrice(it.price)
            binding.discount.text = formatDiscount(it.price, it.previous_price)
        }

        viewModel.commentsLiveData.observe(this) {
            Timber.i(it.toString())
            commentAdapter.comments = it as ArrayList<Comment>
            if (it.size > 3) {
                binding.allComments.visibility = View.VISIBLE
                binding.allComments.setOnClickListener {
                    startActivity(Intent(this, CommentListActivity::class.java).apply {
                        putExtra(EXTRA_KEY, viewModel.productLivedata.value)
                    })
                }
            }
        }

        viewModel.progressBarLivedata.observe(this) {
            setProgressBarIndicator(it)
        }
    }
}