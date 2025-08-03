package com.example.baseproject.ui.auth.splash

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.baseproject.base.BaseFragment
import com.example.baseproject.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment: BaseFragment<FragmentSplashBinding, SplashViewmodel>() {

    val viewModel : SplashViewmodel by viewModels()

    override fun provideViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater,container,false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun provideViewModel(): SplashViewmodel {
        return viewModel
    }

    override fun setUpView() {

    }

    override fun setUpEvent() {
        Handler().postDelayed(
            {
                val action = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                findNavController().navigate(action)
            }
            ,2000
        )

    }
}