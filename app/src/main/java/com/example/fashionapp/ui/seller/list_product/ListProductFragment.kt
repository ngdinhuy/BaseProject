package com.example.fashionapp.ui.seller.list_product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fashionapp.Define
import com.example.fashionapp.adapter.ShopAdapter
import com.example.fashionapp.component.SpacesItemDecoration
import com.example.fashionapp.databinding.FragmentSellerListProductBinding
import com.example.fashionapp.databinding.FragmentSellerListProductBindingImpl
import com.example.fashionapp.model.Product
import com.example.fashionapp.ui.fashion.shop.list_product.ListProductFragmentDirections
import com.example.fashionapp.ui.seller.SellerViewmodel
import com.example.fashionapp.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListProductFragment: Fragment(), ShopAdapter.GoToDetailEvent{
    lateinit var databinding : FragmentSellerListProductBinding
    val viewmodel by viewModels<ListProductViewmodel> ()
    val sellerViewmodel by viewModels<SellerViewmodel>(ownerProducer = { requireParentFragment().requireParentFragment() })

    val adapter : ShopAdapter by lazy {
        ShopAdapter(requireContext(), viewmodel.listProduct.value ?: listOf())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        databinding = FragmentSellerListProductBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = this@ListProductFragment.viewmodel
        }
        return databinding.root
    }

    override fun onResume() {
        super.onResume()
        viewmodel.getListProductBySeller()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        setUpEvent()
    }

    private fun setUpAdapter() {
        databinding.rvProduct.apply {
            adapter = this@ListProductFragment.adapter.apply {
                goToDetailEvent = this@ListProductFragment
            }
            layoutManager = GridLayoutManager(requireContext(), Define.SPAN_COUNT_GRIDLAYOUT_CATEGORY)
            addItemDecoration(SpacesItemDecoration(10))
        }
    }

    private fun setUpEvent() {
        viewmodel.listProduct.observe(viewLifecycleOwner, Observer {
            adapter.apply {
                list = it
                notifyDataSetChanged()
            }
        })

        databinding.tvAddProduct.setOnClickListener {
            sellerViewmodel.goToAddProduct()
        }
    }

    override fun goToDetail(product: Product) {
        sellerViewmodel.goToDetailScreen(product)
    }
}