package com.example.funstore.ui.payment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.funstore.R
import com.example.funstore.common.viewBinding
import com.example.funstore.databinding.FragmentAddressBinding

class AddressFragment : Fragment(R.layout.fragment_address) {

    private val binding by viewBinding(FragmentAddressBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

        }
    }
}