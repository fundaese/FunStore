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
import com.example.funstore.data.model.AddToCartRequest
import com.example.funstore.data.model.ClearCartRequest
import com.example.funstore.data.model.DeleteFromCartRequest
import com.example.funstore.data.model.ProductUI
import com.example.funstore.databinding.FragmentCartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart), CartProductsAdapter.CartProductListener {

    private val binding by viewBinding(FragmentCartBinding::bind)

    private val viewModel by viewModels<CartViewModel>()

    private val cartProductsAdapter by lazy { CartProductsAdapter(this) }

    private lateinit var auth: FirebaseAuth

    private lateinit var userId: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        userId = auth.currentUser!!.uid
        val request = ClearCartRequest(userId)

        viewModel.getCartProducts(userId)

        with (binding) {
            rvCartProducts.adapter = cartProductsAdapter

            btnClear.setOnClickListener {
                viewModel.clearProduct(request)
                viewModel.getCartProducts(userId)
            }

            btnBuy.setOnClickListener {
            }
        }

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

                    var totalAmount = 0.0
                    for (product in state.products) {
                        totalAmount += product.price
                    }

                    tvTotal.text = "${totalAmount} ₺"
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

    override fun onDeleteClick(id: Int) {
        val request = DeleteFromCartRequest(id)
        viewModel.deleteProduct(request)
        viewModel.getCartProducts(userId)
    }
}