package com.example.fashionapp.ui.fashion.profile.order_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fashionapp.databinding.FragmentOrderDetailBinding
import com.example.fashionapp.ui.fashion.profile.list_bill.MyOrderViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailFragment : Fragment(), EventClick {
    lateinit var databinding: FragmentOrderDetailBinding
    val viewmodel: OrderDetailViewmodel by viewModels()
    val args: OrderDetailFragmentArgs by navArgs()
    lateinit var adapter: OrderDetailAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        databinding = FragmentOrderDetailBinding.inflate(inflater, container, false).apply {
            viewmodel = this@OrderDetailFragment.viewmodel.apply {
                infoOrder.value = args.orderModel
            }
        }
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        setUpEvent()
        viewmodel.getAllItemOrder()
    }

    private fun setUpEvent() {
        viewmodel.listOrderItem.observe(viewLifecycleOwner, Observer {
            adapter.listOrderDetail = it
            adapter.notifyDataSetChanged()
        })
    }

    private fun setUpAdapter() {
        adapter = OrderDetailAdapter(listOf(), requireContext())
        databinding.rvOrderItem.apply {
            adapter = this@OrderDetailFragment.adapter.apply {
                eventClick = this@OrderDetailFragment
            }
        }
    }

    override fun clickOpenDetailProduct(idProduct: Int) {
        val action = OrderDetailFragmentDirections.actionGlobalDetailProductFragment(idProduct)
        findNavController().navigate(action)
    }

    override fun clickOpenReview(idOrderItem: Int) {
        val action = OrderDetailFragmentDirections.actionOrderDetailFragmentToReviewFragment(idOrderItem)
        findNavController().navigate(action)
    }
}