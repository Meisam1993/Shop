package com.example.shop.base

import android.content.Context
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children
import com.example.shop.R
import java.lang.IllegalStateException

abstract class BaseActivity : AppCompatActivity(), BaseView {
    override val rootView: CoordinatorLayout?
        get() {
            val viewGroup = window.decorView.findViewById(android.R.id.content) as ViewGroup
            if (viewGroup !is CoordinatorLayout) {
                viewGroup.children.forEach {
                    if (it is CoordinatorLayout) {
                        return it
                    }
                }
                throw IllegalStateException("viewGroup should be instance of CoordinatorLayout")
            } else {
                return viewGroup
            }
        }

    override val viewContext: Context?
        get() = this
}