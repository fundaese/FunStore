package com.example.funstore.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.funstore.R
import com.example.funstore.common.viewBinding
import com.example.funstore.databinding.FragmentProfileBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private lateinit var auth: FirebaseAuth
    private var bottomNavigationView: BottomNavigationView? = null

    private val sharedPreferencesName = "MyPreferences"
    private val keyGender = "userGender"
    private val keyAvatar = "userAvatar"
    private lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)

        // Get gender and avatar from shared preferences
        val savedGender = sharedPreferences.getString(keyGender, null)

        if (savedGender != null) {
            if (savedGender == "boy") {
                binding.rbBoy.isChecked = true
                binding.ivProfile.setImageResource(R.drawable.avatarboy)
            } else if (savedGender == "girl") {
                binding.rbGirl.isChecked = true
                binding.ivProfile.setImageResource(R.drawable.avatargirl)
            }
        }

        // Bottom Navigation Visibility
        bottomNavigationView = getActivity()?.findViewById(R.id.bottomNavigationView);
        bottomNavigationView?.setVisibility(View.GONE);

        auth = Firebase.auth

        with(binding) {
            ivProfile.setImageResource(R.drawable.avatargirl)

            radioGroup.setOnCheckedChangeListener { group, checkedId ->

                when (checkedId) {
                    R.id.rb_boy -> {
                        ivProfile.setImageResource(R.drawable.avatarboy)
                        saveGender("boy")
                        saveAvatar(R.drawable.avatarboy)
                    }
                    R.id.rb_girl -> {
                        ivProfile.setImageResource(R.drawable.avatargirl)
                        saveAvatar(R.drawable.avatargirl)
                        saveGender("girl")
                    }
                }
            }

            tvEmail.text = auth.currentUser?.email.toString()

            tvProfileFavorites.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToFavoriteFragment())
            }

            tvProfileCart.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToCartFragment())
            }

            btnSignOut.setOnClickListener {
                auth.signOut()
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToSignInFragment())
            }
        }
    }

    // Save gender to shared preferences
    fun saveGender(gender: String) {
        val editor = sharedPreferences.edit()
        editor.putString(keyGender, gender)
        editor.apply()
    }

    private fun saveAvatar(avatarResource: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(keyAvatar, avatarResource)
        editor.apply()
    }

}