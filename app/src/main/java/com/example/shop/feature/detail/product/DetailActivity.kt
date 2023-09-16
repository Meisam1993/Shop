package com.example.shop.feature.detail.product

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shop.R
import com.example.shop.base.BaseActivity
import com.example.shop.base.BaseCompletableObserver
import com.example.shop.base.formatDiscount
import com.example.shop.base.formatPrice
import com.example.shop.databinding.ActivityDetailBinding
import com.example.shop.feature.detail.comment.CommentAdapter
import com.example.shop.services.data.dataclasses.Comment
import com.example.shop.services.service.ImageLoadingService
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : BaseActivity() {
    private val viewModel: DetailViewModel by viewModel()
    private val imageLoadingService: ImageLoadingService by inject()
    private val commentAdapter = CommentAdapter()
    private val compositeDisposable = CompositeDisposable()
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
        binding.addToCart.setOnClickListener {
            viewModel.onAddToCartBtnClick()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        Snackbar.make(
                            rootView as CoordinatorLayout,
                            getString(R.string.add_to_cart_Message),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                })
        }
        binding.commentRv.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
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
            commentAdapter.comments = it as ArrayList<Comment>
        }

        viewModel.progressBarLivedata.observe(this) {
            setProgressBarIndicator(it)
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}