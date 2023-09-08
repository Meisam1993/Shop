package com.example.shop.feature.detail.product

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shop.base.formatDiscount
import com.example.shop.base.formatPrice
import com.example.shop.databinding.ActivityDetailBinding
import com.example.shop.services.service.ImageLoadingService
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private val viewModel: DetailViewModel by viewModel()
    private val imageLoadingService: ImageLoadingService by inject()
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
        binding.back.setOnClickListener {
            finish()
        }
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
    }
}