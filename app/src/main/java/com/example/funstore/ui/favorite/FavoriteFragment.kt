package com.example.funstore.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.funstore.R
import com.example.funstore.common.gone
import com.example.funstore.common.viewBinding
import com.example.funstore.common.visible
import com.example.funstore.data.model.ProductUI
import com.example.funstore.databinding.FragmentFavoriteBinding
import com.example.funstore.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite), FavoriteAdapter.FavProductListener {

    private val binding by viewBinding(FragmentFavoriteBinding::bind)

    private val viewModel by viewModels<FavoriteViewModel>()

    private val favAdapter by lazy { FavoriteAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFavProducts()

        binding.rvFavorites.adapter = favAdapter

        observeData()

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = FavoriteFragmentDirections.actionFavoriteFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)


    }

    private fun observeData() = with(binding) {
        viewModel.favState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is FavState.Loading -> {
                    progressBar.visible()
                }

                is FavState.Data -> {
                    favAdapter.submitList(state.products)
                    progressBar.gone()
                }

                is FavState.Error -> {
                    progressBar.gone()
                }
            }
        }
    }

    override fun onProductClick(id: Int) {
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToProductDetailFragment(id)
        findNavController().navigate(action)
    }

    override fun onDeleteClick(product: ProductUI) {
        viewModel.deleteProduct(product)
    }
}