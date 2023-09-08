package com.example.shop.feature.detail.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.shop.base.BaseViewModel
import com.example.shop.base.EXTRA_KEY
import com.example.shop.services.data.dataclasses.Product

class DetailViewModel(private val state: SavedStateHandle) : BaseViewModel() {
    val productLivedata: MutableLiveData<Product> =
        state.getLiveData<Product>(EXTRA_KEY)

    init {

    }
}