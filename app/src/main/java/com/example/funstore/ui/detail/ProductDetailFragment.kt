package com.example.funstore.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.funstore.R
import com.example.funstore.common.loadImage
import com.example.funstore.common.viewBinding
import com.example.funstore.databinding.FragmentProductDetailBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    private val binding by viewBinding(FragmentProductDetailBinding::bind)

    private val args by navArgs<ProductDetailFragmentArgs>()

    private val viewModel by viewModels<ProductDetailViewModel>()

    private var bottomNavigationView: BottomNavigationView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProductsDetail(args.productId)

        // Bottom Navigation Visibility
        bottomNavigationView = getActivity()?.findViewById(R.id.bottomNavigationView);
        bottomNavigationView?.setVisibility(View.GONE);

        with(binding) {
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }

        observeData()
    }

    private fun observeData()  = with(binding) {
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            binding.detailProgressBar.isVisible = it
        }

        viewModel.productsDetailLiveData.observe(viewLifecycleOwner) { product ->
            val scaledRating = (product?.rate?.toFloat()!! / 5.0f) * 100.0f // Rate değerini 0 ile 100 arasında bir orana dönüştürün

            if (product != null) {
                tvTitle.text = product.title
                tvDesc.text = product.description
                tvCategory.text = product.category
                tvPrice.text = "Fiyat: ${product.price} ₺"
                ivProduct.loadImage(product.imageOne)
                ratingBar.rating = scaledRating / 20.0f
            } else {
                Snackbar.make(requireView(), "Product Not Found", 1000).show()
            }
        }

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), it, 1000).show()
        }
    }
}