package com.example.fashionapp.ui.seller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fashionapp.R
import com.example.fashionapp.databinding.FragmentSellerBinding
import com.example.fashionapp.ui.fashion.shop.list_product.ListProductFragmentDirections
import com.example.fashionapp.utils.EventObserver
import com.example.fashionapp.utils.Prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SellerFragment : Fragment() {
    lateinit var databinding: FragmentSellerBinding
    val viewmodel : SellerViewmodel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        databinding = FragmentSellerBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpNavigation()
        setUpEvent()
    }

    private fun setUpEvent() {
        viewmodel.goToDetailScreenEvent.observe(viewLifecycleOwner, EventObserver{
            val action = SellerFragmentDirections.actionGlobalDetailProductFragment(it._id ?: 0)
            findNavController().navigate(action)
        })

        viewmodel.goToAddProductEvent.observe(viewLifecycleOwner, EventObserver{
            val action = SellerFragmentDirections.actionSellerFragmentToAddProductFragment()
            findNavController().navigate(action)
        })

        viewmodel.goToSettingEvent.observe(viewLifecycleOwner, EventObserver{
            val action = SellerFragmentDirections.actionGlobalSettingFragment()
            findNavController().navigate(action)
        })

        viewmodel.goToSplashEvent.observe(viewLifecycleOwner, EventObserver{
            Prefs.newInstance(requireContext()).setId(0)
            val action = SellerFragmentDirections.actionGlobalSplashFragment()
            findNavController().navigate(action)
        })

        viewmodel.goToChatListEvent.observe(viewLifecycleOwner, EventObserver{
            val action = SellerFragmentDirections.actionSellerFragmentToFavoriteFragment2()
            findNavController().navigate(action)
        })

        viewmodel.goToListOrderEvent.observe(viewLifecycleOwner, EventObserver{
            val action = SellerFragmentDirections.actionSellerFragmentToListOrderSellerFragment()
            findNavController().navigate(action)
        })
    }

    private fun setUpNavigation() {
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        databinding.bottomNav.setupWithNavController(navHostFragment.navController)
    }
}