package com.example.fashionapp.ui.fashion.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fashionapp.adapter.HomeAdapter
import com.example.fashionapp.adapter.HomeItemListAdapter
import com.example.fashionapp.databinding.FragmentHomeBinding
import com.example.fashionapp.model.Product
import com.example.fashionapp.ui.fashion.FashionViewmodel
import com.example.fashionapp.utils.Event
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment(), HomeItemListAdapter.GoToDetailEvent {
    lateinit var databinding: FragmentHomeBinding
    val homeViewmodel by viewModels<HomeViewmodel>()
    val fashionViewmodel by viewModels<FashionViewmodel>(ownerProducer = { requireParentFragment().requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = homeViewmodel.apply {
                fashionViewmodel = this@HomeFragment.fashionViewmodel
            }
        }
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpData()
        setUpEvent()
    }

    private fun setUpData() {
        homeViewmodel.getProductByCategory()
    }

    private fun setUpEvent() {
//        homeViewmodel.mapInfo.observe(viewLifecycleOwner, Observer {
//            var homeAdapter = HomeAdapter(requireContext(), it, this@HomeFragment)
//            databinding.rvCategory.apply {
//                adapter = homeAdapter
//                layoutManager = LinearLayoutManager(requireContext())
//            }
//        })

        homeViewmodel.infoCateProduct.observe(viewLifecycleOwner, Observer {
            it?.let {
                var homeAdapter = HomeAdapter(requireContext(),it, this@HomeFragment)
                databinding.rvCategory.apply {
                    adapter = homeAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                }
            }
        })
    }

    override fun goToDetail(product: Product) {
        fashionViewmodel._goToDetailEvent.value = Event(product)
    }
}