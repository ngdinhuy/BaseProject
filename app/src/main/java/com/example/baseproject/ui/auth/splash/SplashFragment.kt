package com.example.baseproject.ui.auth.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.baseproject.base.BaseFragment
import com.example.baseproject.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewmodel>() {

    private val splashViewmodel: SplashViewmodel by viewModels()

    override fun provideViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun provideViewModel(): SplashViewmodel {
        return splashViewmodel
    }

    override fun setUpView() {
    }

    override fun setUpEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(2000)
            val action = SplashFragmentDirections.actionSplashFragmentToTestFragment()
            findNavController().navigate(action)
        }
    }
}
