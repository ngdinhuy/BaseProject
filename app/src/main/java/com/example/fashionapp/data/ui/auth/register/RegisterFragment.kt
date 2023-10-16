package com.example.fashionapp.data.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.fashionapp.databinding.FragmentRegisterBinding
import com.example.fashionapp.ui.fashion.FashionFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment: Fragment() {
    lateinit var databinding : FragmentRegisterBinding
    val registerViewmodel : RegisterViewmodel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        databinding = FragmentRegisterBinding.inflate(inflater, container, false).apply {
            viewmodel =  this@RegisterFragment.registerViewmodel
            lifecycleOwner = viewLifecycleOwner
        }
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpEvent()
    }

    private fun setUpEvent() {
        registerViewmodel.goToFashion.observe(viewLifecycleOwner, Observer {
            val action = FashionFragmentDirections.actionGlobalFashionFragment()
            findNavController().navigate(action)
        })
    }
}