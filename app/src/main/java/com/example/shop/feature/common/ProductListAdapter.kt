package com.example.shop.feature.common

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shop.R
import com.example.shop.base.formatPrice
import com.example.shop.services.data.dataclasses.Product
import com.example.shop.services.service.ImageLoadingService
import java.lang.IllegalStateException

const val VIEW_TYPE_SMALL = 0
const val VIEW_TYPE_LARGE = 1

class ProductListAdapter(
    var viewType: Int,
    private val imageLoadingService: ImageLoadingService,
    private val context: Context
) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {
    var products = ArrayList<Product>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var productClickListener: OnProductItemClickListener? = null

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.image)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val previousPrice: TextView = itemView.findViewById(R.id.previousPrice)
        private val currentPrice: TextView = itemView.findViewById(R.id.currentPrice)

        fun bindProduct(product: Product) {
            imageLoadingService.loadImage(context, product.image, image)
            title.text = product.title
            previousPrice.text = formatPrice(product.previous_price)
            previousPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            currentPrice.text = formatPrice(product.price)
            itemView.setOnClickListener {
                productClickListener?.onProductClick(product)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutResId = when (viewType) {
            VIEW_TYPE_SMALL -> R.layout.item_product_small
            VIEW_TYPE_LARGE -> R.layout.item_product_large
            else -> throw IllegalStateException("ViewType is not Valid!!")
        }
        return ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindProduct(products[position])
    }


    interface OnProductItemClickListener {

        fun onProductClick(product: Product)
    }
}