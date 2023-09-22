package com.example.shop.base

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.shop.R
import com.example.shop.feature.auth.AuthActivity
import com.google.android.material.snackbar.Snackbar
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

interface BaseView {
    val rootView: CoordinatorLayout?
    val viewContext: Context?

    fun setProgressBarIndicator(mustShow: Boolean) {
        rootView?.let {
            viewContext?.let { context ->
                var loadingView = it.findViewById<View>(R.id.loadingView)
                if (loadingView == null && mustShow) {
                    loadingView =
                        LayoutInflater.from(viewContext).inflate(R.layout.loading_view, it, false)
                    it.addView(loadingView)
                }

                loadingView?.visibility = if (mustShow) View.VISIBLE else View.GONE
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showError(baseException: BaseException) {
        viewContext?.let {
            return when (baseException.type) {
                BaseException.Type.SIMPLE ->
                    showSnackBar(
                        baseException.serverMessage
                            ?: it.getString(baseException.userFriendlyMessage)
                    )

                BaseException.Type.AUTH -> {
                    it.startActivity(Intent(it, AuthActivity::class.java))
                    Toast.makeText(it, baseException.serverMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
        rootView?.let {
            Snackbar.make(it, message, duration).show()
        }
    }

    fun showEmptyState(layoutResId: Int): View? {
        rootView?.let {
            viewContext?.let {context ->
                var emptyState = it.findViewById<View>(layoutResId)
            }
        }
    }
}