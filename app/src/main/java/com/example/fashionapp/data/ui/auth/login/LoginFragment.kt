package com.example.fashionapp.data.ui.auth.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.fashionapp.databinding.FragmentLoginBinding
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment:Fragment() {
    private lateinit var databinding : FragmentLoginBinding
    val loginViewmodel : LoginViewmodel by viewModels()
    val navArgs by navArgs<LoginFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = FragmentLoginBinding.inflate(inflater, container, false)
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}