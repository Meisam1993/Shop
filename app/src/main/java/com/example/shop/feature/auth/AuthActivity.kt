package com.example.shop.feature.auth

import android.os.Bundle
import com.example.shop.R
import com.example.shop.base.BaseActivity
import com.example.shop.databinding.ActivityAuthBinding

class AuthActivity : BaseActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initViews()
    }

    private fun initViews() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, LoginFragment())
                .commit()
        }
    }
}