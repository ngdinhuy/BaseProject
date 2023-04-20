package com.example.fashionapp.ui.fashion.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fashionapp.databinding.FragmentProfileBinding
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: Fragment() {
    lateinit var databinding: FragmentProfileBinding
    val viewmodel by viewModels<ProfileViewmodel> ()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = FragmentProfileBinding.inflate(inflater, container, false)
        return databinding.root
    }
}