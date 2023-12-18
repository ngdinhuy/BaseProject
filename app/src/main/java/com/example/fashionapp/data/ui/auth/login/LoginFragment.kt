package com.example.fashionapp.data.ui.auth.login

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fashionapp.R
import com.example.fashionapp.Role
import com.example.fashionapp.data.ui.auth.splash.SplashFragmentDirections
import com.example.fashionapp.databinding.FragmentLoginBinding
import com.example.fashionapp.ui.fashion.FashionFragment
import com.example.fashionapp.ui.fashion.FashionFragmentDirections
import com.example.fashionapp.ui.loading.LoadingFragmentDirections
import com.example.fashionapp.utils.EventObserver
import com.example.fashionapp.utils.Prefs
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var databinding: FragmentLoginBinding
    val loginViewmodel: LoginViewmodel by viewModels()
    val navArgs by navArgs<LoginFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = FragmentLoginBinding.inflate(inflater, container, false).apply {
            viewmodel = loginViewmodel
            lifecycleOwner = viewLifecycleOwner
        }
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text = getText(R.string.have_account)
        val content = SpannableString(text)
        content.setSpan(UnderlineSpan(), 18, text.length, 0)
        content.setSpan(ForegroundColorSpan(Color.RED), 18, text.length, 0)
        databinding.tvRegister.text = content
        setUpEvent()
    }

    private fun setUpEvent() {
        loginViewmodel.stateLogin.observe(viewLifecycleOwner, EventObserver {
            if (it == Role.SELLER){
                val action = SplashFragmentDirections.actionGlobalSellerFragment()
                findNavController().navigate(action)
            } else {
                val action = SplashFragmentDirections.actionGlobalFashionFragment()
                findNavController().navigate(action)
            }
        })

        loginViewmodel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                val action = LoadingFragmentDirections.actionGlobalLoadingFragment()
                findNavController().navigate(action)
            } else {
                findNavController().popBackStack()
            }
        })

        loginViewmodel.goToRegisterEvent.observe(viewLifecycleOwner, EventObserver {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        })
    }
}