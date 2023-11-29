package com.example.fashionapp.ui.seller.list_order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fashionapp.databinding.FragmentListOrderSellerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListOrderSellerFragment: Fragment() {
    val viewmodel : ListOrderSellerViewmodel by viewModels()
    lateinit var databinding : FragmentListOrderSellerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        databinding = FragmentListOrderSellerBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = this@ListOrderSellerFragment.viewmodel
        }
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.loadListOrderItem()
        setUpEvent()
    }

    private fun setUpEvent() {
        databinding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewmodel.listOrderItem.observe(viewLifecycleOwner, Observer {
            val orderItemSellerAdapter = OrderItemSellerAdapter(it, requireContext())
            databinding.rvOrderItem.apply {
                adapter = orderItemSellerAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        })
    }
}