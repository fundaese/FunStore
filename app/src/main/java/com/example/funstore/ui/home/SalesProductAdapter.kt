package com.example.funstore.ui.home

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.funstore.common.loadImage
import com.example.funstore.data.model.Product
import com.example.funstore.databinding.ItemSalesProductBinding

class SalesProductAdapter (
    private val productListener: ProductListener
) : ListAdapter<Product, SalesProductAdapter.SalesProductViewHolder>(ProductDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesProductViewHolder =
        SalesProductViewHolder(
            ItemSalesProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            productListener
        )

    override fun onBindViewHolder(holder: SalesProductViewHolder, position: Int) =
        holder.bind(getItem(position))

    class SalesProductViewHolder(
        private val binding: ItemSalesProductBinding,
        private val productListener: ProductListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) = with(binding) {
            tvTitle.text = product.title
            tvCategory.text = product.category
            tvPrice.text = "${product.price} ₺"

            imgProduct.loadImage(product.imageOne)

            if (product.saleState == true) {
                tvSalePrice.isVisible = true
                tvSalePrice.text = "${product.salePrice} ₺"
                tvPrice.text = "${product.price} ₺"
                tvPrice.paintFlags = tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                tvPrice.text = "${product.price} ₺"
                tvSalePrice.isVisible = false
            }

            root.setOnClickListener {
                productListener.onProductClick(product.id ?: 1)
            }
        }
    }

    class ProductDiffCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    interface ProductListener {
        fun onProductClick(id: Int)
    }
}