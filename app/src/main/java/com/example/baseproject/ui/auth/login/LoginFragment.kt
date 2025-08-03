package com.example.baseproject.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.baseproject.base.BaseFragment
import com.example.baseproject.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: BaseFragment<FragmentLoginBinding, LoginViewmodel>() {
    private lateinit var databinding : FragmentLoginBinding
    val viewmodel : LoginViewmodel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun provideViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun provideViewModel(): LoginViewmodel {
        return viewmodel
    }

    override fun setUpView() {

    }

    override fun setUpEvent() {
        super.setUpEvent()
    }
}