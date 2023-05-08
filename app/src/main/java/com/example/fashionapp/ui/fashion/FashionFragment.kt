package com.example.fashionapp.ui.fashion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fashionapp.R
import com.example.fashionapp.databinding.FragmentFashionBinding
import com.example.fashionapp.ui.fashion.detail_product.DetailProductFragmentDirections
import com.example.fashionapp.ui.loading.LoadingFragmentDirections
import com.example.fashionapp.utils.EventObserver
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpNavigation()
        setupEvent()
    }

    private fun setupEvent() {
        viewmodel.isLoading.observe(this, EventObserver {
            if (it) {
                val action = LoadingFragmentDirections.actionGlobalLoadingFragment()
                findNavController().navigate(action)
            } else {
                findNavController().popBackStack()
            }
        })

        viewmodel.goToDetailEvent.observe(this, EventObserver {
            val action = FashionFragmentDirections.actionFashionFragmentToDetailProductFragment(it)
            findNavController().navigate(action)
        })
    }

    private fun setUpNavigation() {
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        databinding.bottomNav.setupWithNavController(navHostFragment.navController)
    }

}