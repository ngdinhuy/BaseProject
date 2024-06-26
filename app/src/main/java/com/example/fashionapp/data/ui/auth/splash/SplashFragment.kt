 package com.example.fashionapp.data.ui.auth.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fashionapp.Role
import com.example.fashionapp.databinding.FrgamentSplashBinding
import com.example.fashionapp.utils.Prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment:Fragment() {
    private lateinit var databinding : FrgamentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = FrgamentSplashBinding.inflate(inflater,container,false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed(
            {
                if (Prefs.newInstance(requireContext()).getId() == 0){
                    val action = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                    findNavController().navigate(action)
                } else{
                    if (Prefs.newInstance(requireContext()).getRole() == Role.SELLER){
                        val action = SplashFragmentDirections.actionGlobalSellerFragment()
                        findNavController().navigate(action)
                    } else {
                        val action = SplashFragmentDirections.actionGlobalFashionFragment()
                        findNavController().navigate(action)
                    }
                }

            },2000
        )

    }
}