package com.example.fashionapp.ui.fashion.shop.list_product

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
import com.example.fashionapp.adapter.ListProductAdapter
import com.example.fashionapp.databinding.FragmentListProductBinding
import com.example.fashionapp.model.Product
import com.example.fashionapp.ui.fashion.detail_product.DetailProductFragmentDirections
import com.example.fashionapp.ui.fashion.shop.ShopViewmodel
import com.example.fashionapp.ui.loading.LoadingFragmentDirections
import com.example.fashionapp.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListProductFragment : Fragment(), ListProductAdapter.ItemClickEvent {
    val viewmodel by viewModels<ListProductViewmodel>()
    lateinit var databinding: FragmentListProductBinding
    val args by navArgs<ListProductFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = FragmentListProductBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = this@ListProductFragment.viewmodel
            category = args.category
        }
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.getProductInCategory(args.category)
        setupAdapter()
        setUpEvent()
    }

    private fun setUpEvent() {
        viewmodel.isLoading.observe(this, EventObserver {
            if (it) {
                val action = LoadingFragmentDirections.actionGlobalLoadingFragment()
                findNavController().navigate(action)
            } else {
                findNavController().popBackStack()
            }
        })

        viewmodel.backEvent.observe(this, EventObserver{
            findNavController().popBackStack()
        })
    }

    private fun setupAdapter() {
        viewmodel.listProduct.observe(this, Observer{
            databinding.rvCategory.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = ListProductAdapter(requireContext(), it).apply {
                    passValueItemClickEvent(this@ListProductFragment)
                }
            }
        })
    }

    override fun goToProductDetail(product: Product) {
        val action = DetailProductFragmentDirections.actionGlobalDetailProductFragment(product)
        findNavController().navigate(action)
    }
}