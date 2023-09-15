package com.example.funstore.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.funstore.R
import com.example.funstore.common.viewBinding
import com.example.funstore.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), ProductAdapter.ProductListener, SalesProductAdapter.ProductListener {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private var bottomNavigationView: BottomNavigationView? = null
    private val productAdapter by lazy { ProductAdapter(this) }
    private val viewModel by viewModels<HomeViewModel>()
    private val salesProductAdapter by lazy { SalesProductAdapter(this) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bottom Navigation Visibility
        bottomNavigationView = getActivity()?.findViewById(R.id.bottomNavigationView);
        bottomNavigationView?.setVisibility(View.VISIBLE);

        with(binding) {
            rvAllProducts.adapter = productAdapter
            rvDiscountedProducts.adapter = salesProductAdapter
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                if (checkedId == R.id.rb_all) {
                    viewModel.getProducts()
                } else {
                    val category = when (checkedId) {
                        R.id.rb_adult -> "adult"
                        R.id.rb_kid -> "kid"
                        else -> "all"
                    }
                    viewModel.getProductsByCategory(category)
                }
            }
        }

        viewModel.getProducts()
        viewModel.getSaleProducts()

        observeData()
    }

    private fun observeData() {
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            binding.progressBar2.isVisible = it
        }

        viewModel.productLiveData.observe(viewLifecycleOwner) { list ->
            if(list != null) {
                productAdapter.submitList(list)
            } else {
                Snackbar.make(requireView(), "Empty List", 1000).show()
            }
        }

        viewModel.salesProductLiveData.observe(viewLifecycleOwner) { list ->
            if(list != null) {
                salesProductAdapter.submitList(list)
            } else {
                Snackbar.make(requireView(), "Empty List", 1000).show()
            }
        }

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), it, 1000).show()
        }
    }

    override fun onProductClick(id: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(id)
        findNavController().navigate(action)
    }

    override fun onFavoriteClick(id: Int) {

    }
}