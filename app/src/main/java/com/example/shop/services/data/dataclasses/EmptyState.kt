package com.example.shop.services.data.dataclasses

import androidx.annotation.StringRes

data class EmptyState(
    val mustShow: Boolean,
    @StringRes val messageResId: Int = 0,
    val callToActionBtnVisibility: Boolean = false,
    @StringRes val callToActionBtnMessageResId: Int = 0
)