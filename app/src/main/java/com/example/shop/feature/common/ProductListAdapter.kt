package com.example.shop.feature.common

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shop.base.formatPrice
import com.example.shop.services.data.dataclasses.Product
import com.example.shop.databinding.ItemProductSmallBinding
import com.example.shop.services.service.ImageLoadingService

class ProductListAdapter(
    private val context: Context,
    private val imageLoadingService: ImageLoadingService
) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {
    var products = ArrayList<Product>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var listener: OnProductItemClickListener? = null

    inner class ProductViewHolder(private val binding: ItemProductSmallBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindProduct(product: Product) {
            imageLoadingService.loadImage(context, product.image, binding.image)
            binding.title.text = product.title
            binding.previousPrice.text = formatPrice(product.previous_price)
            binding.previousPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.currentPrice.text = formatPrice(product.price)
            itemView.setOnClickListener {
                listener?.onProductClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductSmallBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.bindProduct(products[position])
}

interface OnProductItemClickListener {

    fun onProductClick(product: Product)
}