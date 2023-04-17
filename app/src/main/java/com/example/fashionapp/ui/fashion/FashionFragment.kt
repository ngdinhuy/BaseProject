package com.example.fashionapp.ui.fashion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fashionapp.databinding.FragmentFashionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FashionFragment : Fragment() {
    lateinit var databinding: FragmentFashionBinding
    val viewmodel: FashionViewmodel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = FragmentFashionBinding.inflate(inflater, container, false)
        return databinding.root
    }
}