package com.example.funstore.ui.search

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.funstore.R
import com.example.funstore.common.gone
import com.example.funstore.common.viewBinding
import com.example.funstore.common.visible
import com.example.funstore.databinding.FragmentSearchBinding
import com.example.funstore.ui.favorite.FavoriteFragmentDirections
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), SearchAdapter.SearchProductListener {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    private val viewModel by viewModels<SearchViewModel>()

    private val searchAdapter by lazy { SearchAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvSearch.adapter = searchAdapter

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { query ->
                        if (query.length >= 3) {
                            viewModel.getSearchProduct(query)
                        } else {
                            Snackbar.make(requireView(), "Enter at least 3 characters for the product you want to search", 1000).show()
                        }
                    }
                    return true
                }
            })
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = SearchFragmentDirections.actionSearchFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.searchState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SearchState.Loading -> {
                    progressBar2.visible()
                }

                is SearchState.Data -> {
                    progressBar2.gone()
                    searchAdapter.submitList(state.products)
                }

                is SearchState.Error -> {
                    progressBar2.gone()
                }
            }
        }
    }
    override fun onProductClick(id: Int) {
        val action = SearchFragmentDirections.actionSearchFragmentToProductDetailFragment(id)
        findNavController().navigate(action)
    }
}