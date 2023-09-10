package com.example.shop.base

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.shop.R

class BaseToolbar(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    var backBtn : ImageView

    init {
        inflate(context, R.layout.toolbar_view, this)
        backBtn = findViewById<ImageView>(R.id.back)

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.BaseToolbar)
            val title = a.getString(R.styleable.BaseToolbar_bt_title)
            if (!title.isNullOrEmpty()) {
                val toolbarTitle = findViewById<TextView>(R.id.toolbarTitle)
                toolbarTitle.text = title

                a.recycle()
            }
        }
    }
}