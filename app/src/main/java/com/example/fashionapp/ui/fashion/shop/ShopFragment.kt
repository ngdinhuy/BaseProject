package com.example.fashionapp.ui.fashion.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fashionapp.Define
import com.example.fashionapp.R
import com.example.fashionapp.adapter.ShopAdapter
import com.example.fashionapp.component.FilterBottomSheetDialog
import com.example.fashionapp.component.OnFilterClick
import com.example.fashionapp.component.SpacesItemDecoration
import com.example.fashionapp.databinding.FragmentShopBinding
import com.example.fashionapp.model.Product
import com.example.fashionapp.ui.fashion.FashionViewmodel
import com.example.fashionapp.utils.Event
import com.example.fashionapp.utils.EventObserver
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopFragment : Fragment(), ShopAdapter.GoToDetailEvent, OnFilterClick {
    lateinit var databinding: FragmentShopBinding
    val viewmodel by viewModels<ShopViewmodel>()
    val fashionViewmodel by viewModels<FashionViewmodel>(ownerProducer = { requireParentFragment().requireParentFragment() })

    private val shopAdapter: ShopAdapter by lazy {
        ShopAdapter(requireContext(), viewmodel.listProduct.value ?: listOf<Product>())
    }

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
        viewmodel.getListProduct(1)
    }

    private fun setUpEvent() {
        viewmodel.listCategories.observe(viewLifecycleOwner, EventObserver {
            it.forEach { category ->
                Chip(requireContext(), null, R.attr.CustomChipStyle).apply {
                    text = category.title
                    isClickable = true
                    isCheckable = true
                    isCheckedIconVisible = false
                    isFocusable = true
                    setEnsureMinTouchTargetSize(false)
                    if (it[0] == category) {
                        isChecked = true
                    }
                    setOnClickListener {
                        viewmodel.getListProduct(category.id_)
                        viewmodel.category = category.id_
                    }
                    databinding.chipGroupCategory.addView(this)
                }
            }
        })

        databinding.clFilter.setOnClickListener {
            val filterBottomSheet = FilterBottomSheetDialog().apply {
                onFilterClick = this@ShopFragment
                filter = viewmodel.filter
            }.show(activity?.supportFragmentManager!!,"")
        }
    }

    private fun setUpAdapter() {
        databinding.rvShop.apply {
            adapter = shopAdapter.apply {
                goToDetailEvent = this@ShopFragment
            }
            layoutManager = GridLayoutManager(requireContext(), Define.SPAN_COUNT_GRIDLAYOUT_CATEGORY)
            addItemDecoration(SpacesItemDecoration(10))
        }
        viewmodel.listProduct.observe(viewLifecycleOwner, Observer {
            shopAdapter.apply {
                list = it
                notifyDataSetChanged()
            }
        })


    }

    override fun goToDetail(product: Product) {
        fashionViewmodel._goToDetailEvent.value = Event(product)
    }

    override fun filterClick(filter: Int) {
        viewmodel.updateFilter(filter)
    }

}