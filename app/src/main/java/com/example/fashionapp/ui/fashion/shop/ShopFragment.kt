package com.example.fashionapp.ui.fashion.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fashionapp.R
import com.example.fashionapp.adapter.ShopAdapter
import com.example.fashionapp.databinding.FragmentShopBinding
import com.example.fashionapp.ui.fashion.FashionViewmodel
import com.example.fashionapp.utils.Event
import com.example.fashionapp.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopFragment: Fragment(), ShopAdapter.ItemClickEvent {
    lateinit var databinding: FragmentShopBinding
    val viewmodel by viewModels<ShopViewmodel> ()
    val fashionViewmodel by viewModels<FashionViewmodel>(ownerProducer = { requireParentFragment().requireParentFragment() })
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = FragmentShopBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = this@ShopFragment.viewmodel.apply {
                fashionViewModel = this@ShopFragment.fashionViewmodel
            }
        }
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.getAllCategories()
        setUpAdapter()
        setUpEvent()
    }

    private fun setUpEvent() {

    }

    private fun setUpAdapter() {
        viewmodel.listCategories.observe(this, EventObserver{
            databinding.rvShop.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = ShopAdapter(requireContext(), it).apply {
                    passDataItemClickEvent(this@ShopFragment)
                }
            }
        })
    }

    override fun itemClick(cate: String) {
        fashionViewmodel._goToListProductEvent.value = Event(cate)
    }


}