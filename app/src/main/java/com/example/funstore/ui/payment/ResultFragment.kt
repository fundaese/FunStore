package com.example.funstore.ui.payment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.funstore.R
import com.example.funstore.common.viewBinding
import com.example.funstore.databinding.FragmentResultBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ResultFragment : Fragment(R.layout.fragment_result) {

    private val binding by viewBinding(FragmentResultBinding::bind)
    private var bottomNavigationView: BottomNavigationView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bottom Navigation Visibility
        bottomNavigationView = getActivity()?.findViewById(R.id.bottomNavigationView);
        bottomNavigationView?.setVisibility(View.GONE);

        val gifImg = pl.droidsonroids.gif.GifDrawable(resources, R.drawable.cargo)

        with(binding) {
            ivCargo.setImageDrawable(gifImg)
            textView.setText("Your products have been delivered to the cargo")
            btnHome.setOnClickListener {
                findNavController().navigate(ResultFragmentDirections.actionResultFragmentToHomeFragment())
            }
        }
    }
}