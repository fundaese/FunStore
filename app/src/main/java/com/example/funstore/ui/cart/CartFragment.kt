package com.example.funstore.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.funstore.R
import com.example.funstore.common.gone
import com.example.funstore.common.viewBinding
import com.example.funstore.common.visible
import com.example.funstore.data.model.ProductUI
import com.example.funstore.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart), CartProductsAdapter.CartProductListener {

    private val binding by viewBinding(FragmentCartBinding::bind)

    private val viewModel by viewModels<CartViewModel>()

    private val cartProductsAdapter by lazy { CartProductsAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCartProducts()

        binding.rvCartProducts.adapter = cartProductsAdapter

        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.cartState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is CartState.Loading -> {
                    progressBar.visible()
                }

                is CartState.Data -> {
                    cartProductsAdapter.submitList(state.products)
                    progressBar.gone()
                }

                is CartState.Error -> {
                    progressBar.gone()
                }
            }
        }
    }

    override fun onProductClick(id: Int) {
        val action = CartFragmentDirections.actionCartFragmentToProductDetailFragment(id)
        findNavController().navigate(action)
    }

    override fun onDeleteClick(product: ProductUI) {
        viewModel.deleteProduct(product)
    }
}