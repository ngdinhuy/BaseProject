package com.example.fashionapp.ui.fashion.seller_shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fashionapp.adapter.HomeAdapter
import com.example.fashionapp.adapter.HomeItemListAdapter
import com.example.fashionapp.databinding.FragmentSellerShopBinding
import com.example.fashionapp.model.Product
import com.example.fashionapp.ui.seller.home.SellerHomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SellerShopFragment: Fragment(), HomeItemListAdapter.GoToDetailEvent {
    val viewmodel: SellerShopViewmodel by viewModels()
    lateinit var databinding : FragmentSellerShopBinding
    val args : SellerShopFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        databinding = FragmentSellerShopBinding.inflate(inflater, container, false).apply {
            viewmodel = this@SellerShopFragment.viewmodel.apply {
                idSeller = args.idSeller
            }
            lifecycleOwner = viewLifecycleOwner
        }
        return databinding.root
    }

    override fun onResume() {
        super.onResume()
        viewmodel.loadInfoSeller()
        viewmodel.getListCategoryProduct()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpEvent()
    }

    private fun setUpEvent() {
        viewmodel.infoCateProduct.observe(viewLifecycleOwner, Observer {
            it?.let {
                var homeAdapter = HomeAdapter(requireContext(),it, this@SellerShopFragment, false)
                databinding.rvCategory.apply {
                    adapter = homeAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                }
            }
        })

        databinding.tvSearch.setOnClickListener {
            val action = SellerShopFragmentDirections.actionGlobalSearchFragment(viewmodel.idSeller)
            findNavController().navigate(action)
        }

        databinding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun goToDetail(product: Product) {
        val action = SellerShopFragmentDirections.actionGlobalDetailProductFragment(product._id ?: 0)
        findNavController().navigate(action)
    }
}