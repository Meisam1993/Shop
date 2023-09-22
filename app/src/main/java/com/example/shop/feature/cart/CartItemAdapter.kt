package com.example.shop.feature.cart

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shop.R
import com.example.shop.base.formatPrice
import com.example.shop.services.data.dataclasses.cart.CartItem
import com.example.shop.services.data.dataclasses.cart.PurchaseDetails
import com.example.shop.services.service.ImageLoadingService

const val VIEW_TYPE_CART_ITEM = 0
const val VIEW_TYPE_PURCHASE_DETAILS = 1

class CartItemAdapter(
    val cartItemList: MutableList<CartItem>,
    private val imageLoadingService: ImageLoadingService,
    private val context: Context,
    private val cartItemViewCallBack: CartItemViewCallBack
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var purchaseDetails: PurchaseDetails? = null

    fun removeCartItem(cartItem: CartItem) {
        val index = cartItemList.indexOf(cartItem)
        if (index > -1) {
            cartItemList[index].changeCountProgressBarVisibility = false
            notifyItemRemoved(index)
        }
    }

    fun changeCartItemCount(cartItem: CartItem) {
        val index = cartItemList.indexOf(cartItem)
        if (index > -1) {
            cartItemList[index].changeCountProgressBarVisibility = false
            notifyItemChanged(index)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == cartItemList.size) {
            VIEW_TYPE_CART_ITEM
        } else {
            VIEW_TYPE_PURCHASE_DETAILS
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_CART_ITEM) {
            CartItemViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false)
            )
        } else {
            PurchaseDetailsViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_purchase_details, parent, false)
            )
        }
    }

    override fun getItemCount(): Int {
        return cartItemList.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CartItemViewHolder) {
            holder.bindCartItem(cartItemList[position])
        } else if (holder is PurchaseDetailsViewHolder) {
            purchaseDetails?.let {
                holder.bindPurchaseDetails(it.totalPrice, it.shippingCost, it.payablePrice)
            }
        }
    }

    inner class CartItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.image)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val increaser: ImageView = itemView.findViewById(R.id.increaser)
        private val decreaser: ImageView = itemView.findViewById(R.id.decreaser)
        private val counter: TextView = itemView.findViewById(R.id.counter)
        private val counterProgressBar: ProgressBar = itemView.findViewById(R.id.counterProgressBar)
        private val previousPrice: TextView = itemView.findViewById(R.id.previousPrice)
        private val currentPrice: TextView = itemView.findViewById(R.id.currentPrice)
        private val removeCartItem: TextView = itemView.findViewById(R.id.remove)
        private val removeProgressBar: ProgressBar = itemView.findViewById(R.id.removeProgressBar)

        fun bindCartItem(cartItem: CartItem) {
            imageLoadingService.loadImage(context, cartItem.product.image, image)

            image.setOnClickListener {
                cartItemViewCallBack.onProductImageClick(cartItem)
            }
            title.text = cartItem.product.title
            title.setOnClickListener {
                cartItemViewCallBack.onProductTitleClick(cartItem)
            }

            increaser.setOnClickListener {
                cartItem.changeCountProgressBarVisibility = true
                cartItemViewCallBack.onIncreaserCartItemButtonClick(cartItem)
                counter.visibility = View.INVISIBLE
                counterProgressBar.visibility = View.VISIBLE
            }

            decreaser.setOnClickListener {
                if (cartItem.count > 1) {
                    cartItem.changeCountProgressBarVisibility = true
                    counter.visibility = View.INVISIBLE
                    counterProgressBar.visibility = View.VISIBLE
                    cartItemViewCallBack.onDecreaseCartItemButtonClick(cartItem)
                }
            }

            counter.text = cartItem.count.toString()

            counterProgressBar.visibility =
                if (cartItem.changeCountProgressBarVisibility) View.VISIBLE else View.GONE
            counter.visibility =
                if (cartItem.changeCountProgressBarVisibility) View.INVISIBLE else View.VISIBLE

            previousPrice.text = formatPrice(cartItem.product.previous_price)
            previousPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

            currentPrice.text = formatPrice(cartItem.product.price)

            removeCartItem.setOnClickListener {
                cartItem.changeCountProgressBarVisibility = true
                cartItemViewCallBack.onRemoveCartItemButtonClick(cartItem)
                removeCartItem.visibility = View.INVISIBLE
                removeProgressBar.visibility = View.VISIBLE
            }
        }
    }

    inner class PurchaseDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val total: TextView = itemView.findViewById(R.id.totalPrice)
        private val shipping: TextView = itemView.findViewById(R.id.shippingCost)
        private val payable: TextView = itemView.findViewById(R.id.payablePrice)

        fun bindPurchaseDetails(totalPrice: Int, shippingCost: Int, payablePrice: Int) {
            total.text = formatPrice(totalPrice)
            shipping.text = formatPrice(shippingCost)
            payable.text = formatPrice(payablePrice)
        }
    }

    interface CartItemViewCallBack {

        fun onProductImageClick(cartItem: CartItem)

        fun onProductTitleClick(cartItem: CartItem)

        fun onIncreaserCartItemButtonClick(cartItem: CartItem)

        fun onDecreaseCartItemButtonClick(cartItem: CartItem)

        fun onRemoveCartItemButtonClick(cartItem: CartItem)
    }
}