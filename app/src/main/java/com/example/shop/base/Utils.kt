package com.example.shop.base

import android.content.Context
import android.content.res.Resources
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.util.DisplayMetrics

fun convertDpToPixel(dp: Float, context: Context?): Float {
    return if (context != null) {
        val resources = context.resources
        val metrics = resources.displayMetrics
        dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    } else {
        val metrics = Resources.getSystem().displayMetrics
        dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}

fun formatPrice(price: Number, unitRelativeSizeFactory: Float = 0.7f): SpannableString {
    val currencyLabel = "تومان"
    val spannableString = SpannableString("$price $currencyLabel")
    spannableString.setSpan(
        RelativeSizeSpan(unitRelativeSizeFactory),
        spannableString.indexOf(currencyLabel),
        spannableString.length,
        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannableString
}

fun formatDiscount(currentPrice: Int, previousPrice: Int): String {
    val discount = 100 - (currentPrice * 100) / previousPrice
    return "$discount%"
}