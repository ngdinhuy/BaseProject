package com.example.fashionapp.ui.seller.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fashionapp.databinding.FragmentSellerHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SellerHomeFragment : Fragment(){
    lateinit var databinding : FragmentSellerHomeBinding
    val viewmodel by viewModels<SellerHomeViewmodel> ()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = FragmentSellerHomeBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = this@SellerHomeFragment.viewmodel
        }
        return databinding.root
    }

    override fun onResume() {
        super.onResume()
        viewmodel.getInfoUser()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpEvent()
    }

    private fun setUpEvent() {

    }
}