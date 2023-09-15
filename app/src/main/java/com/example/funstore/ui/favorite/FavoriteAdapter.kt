package com.example.funstore.ui.favorite

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.funstore.common.loadImage
import com.example.funstore.data.model.Product
import com.example.funstore.databinding.ItemFavoriteBinding

class FavoriteAdapter(
    private val productListener: ProductListener
) : ListAdapter<Product, FavoriteAdapter.FavoriteViewHolder>(FavoriteDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
        FavoriteViewHolder(
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            productListener
        )

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) =
        holder.bind(getItem(position))

    class FavoriteViewHolder(
        private val binding: ItemFavoriteBinding,
        private val productListener: ProductListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) = with(binding) {
            imgFavorite.loadImage(product.imageOne)
            tvFavTitle.text = product.title
            tvFavDesc.text = product.description

            if (product.saleState == true) {
                tvFavSalePrice.isVisible = true
                tvFavSalePrice.text = "${product.salePrice} ₺"
                tvFavPrice.text = "${product.price} ₺"
                tvFavPrice.paintFlags = tvFavPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                tvFavPrice.text = "${product.price} ₺"
                tvFavSalePrice.isVisible = false
            }

            root.setOnClickListener {
                productListener.onProductClick(product.id ?: 1)
            }
        }
    }

    class FavoriteDiffCallBack : DiffUtil.ItemCallback<Product>() {
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