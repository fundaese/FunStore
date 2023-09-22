package com.example.funstore.ui.cart

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.funstore.R
import com.example.funstore.common.gone
import com.example.funstore.common.viewBinding
import com.example.funstore.common.visible
import com.example.funstore.data.model.ClearCartRequest
import com.example.funstore.data.model.DeleteFromCartRequest
import com.example.funstore.databinding.FragmentCartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart), CartProductsAdapter.CartProductListener {

    private val binding by viewBinding(FragmentCartBinding::bind)

    private val viewModel by viewModels<CartViewModel>()

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var cartProductsAdapter: CartProductsAdapter

    private lateinit var auth: FirebaseAuth

    private lateinit var userId: String

    private var totalAmount = 0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        userId = auth.currentUser!!.uid
        val request = ClearCartRequest(userId)

        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        cartProductsAdapter = CartProductsAdapter(this, sharedPreferences)


        viewModel.getCartProducts(userId)

        with (binding) {
            rvCartProducts.adapter = cartProductsAdapter

            btnClear.setOnClickListener {
                viewModel.clearProduct(request)
                viewModel.getCartProducts(userId)

                totalAmount = 0.0

                val editor = sharedPreferences.edit()
                for (product in cartProductsAdapter.currentList) {
                    editor.putInt("count_key_${product.id}", 0)
                }
                editor.apply()
            }

            btnBuy.setOnClickListener {
                findNavController().navigate(CartFragmentDirections.actionCartFragmentToPaymentFragment())
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

                    if (state.products.isEmpty()) {
                        rvCartProducts.visibility = View.GONE
                        tvTotal.visibility = View.GONE
                        tvAmount.visibility = View.GONE
                        btnClear.visibility = View.GONE
                        btnBuy.visibility = View.GONE

                        tvError.visibility = View.VISIBLE
                        tvError.setText("There are no products in your cart")
                    } else {
                        rvCartProducts.visibility = View.VISIBLE
                        tvTotal.visibility = View.VISIBLE
                        tvAmount.visibility = View.VISIBLE
                        btnClear.visibility = View.VISIBLE
                        btnBuy.visibility = View.VISIBLE
                        tvError.visibility = View.GONE

                        for (product in cartProductsAdapter.currentList) {
                            val currentCount = sharedPreferences.getInt("count_key_${product.id}", 1)
                            totalAmount += (product.price * currentCount)
                        }


                        tvTotal.text = "${totalAmount} ₺"
                    }


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
        binding.tvTotal.text = "0 ₺"
    }
}