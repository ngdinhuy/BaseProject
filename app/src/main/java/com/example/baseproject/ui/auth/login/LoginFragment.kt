package com.example.baseproject.ui.auth.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.baseproject.base.BaseFragment
import com.example.baseproject.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewmodel>() {

    private val loginViewmodel: LoginViewmodel by viewModels()

    override fun provideViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun provideViewModel(): LoginViewmodel {
        return loginViewmodel
    }

    override fun setUpView() {
    }

    override fun setUpEvent() {
        super.setUpEvent()
    }
}
