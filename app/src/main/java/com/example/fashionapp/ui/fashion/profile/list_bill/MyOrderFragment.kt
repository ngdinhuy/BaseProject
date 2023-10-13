package com.example.fashionapp.ui.fashion.profile.list_bill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fashionapp.adapter.OrderAdapter
import com.example.fashionapp.databinding.FragmentMyOrderBinding
import com.example.fashionapp.ui.fashion.cart.CartViewmodel
import com.example.fashionapp.ui.fashion.profile.order_detail.OrderDetailFragmentDirections
import com.example.fashionapp.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyOrderFragment: Fragment() {
    val viewmodel: MyOrderViewmodel by viewModels()
    lateinit var databinding : FragmentMyOrderBinding
    lateinit var adapter : OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = FragmentMyOrderBinding.inflate(inflater, container, false).apply {
            viewmodel = this@MyOrderFragment.viewmodel
            lifecycleOwner = viewLifecycleOwner
        }
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.getListOrderFromServer()

        setUpAdapter()
    }

    fun setUpAdapter(){
        adapter = OrderAdapter(
            requireContext(),
            listOf()
        ) {
            val action = MyOrderFragmentDirections.actionMyOrderFragmentToOrderDetailFragment(it)
            findNavController().navigate(action)
        }
        databinding.rvOrder.apply {
            adapter = this@MyOrderFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewmodel.listOrder.observe(viewLifecycleOwner, EventObserver{
            adapter.listBill = it
            adapter.notifyDataSetChanged()
        })
    }

}