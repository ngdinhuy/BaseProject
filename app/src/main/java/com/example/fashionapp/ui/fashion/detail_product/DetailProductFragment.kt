package com.example.fashionapp.ui.fashion.detail_product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.fashionapp.Role
import com.example.fashionapp.databinding.FragmentDetailProductBinding
import com.example.fashionapp.ui.fashion.chat.ChatFragment
import com.example.fashionapp.ui.fashion.chat.ChatFragmentDirections
import com.example.fashionapp.ui.fashion.seller_shop.SellerShopFragmentDirections
import com.example.fashionapp.ui.seller.add_product.AddProductFragmentDirections
import com.example.fashionapp.utils.EventObserver
import com.example.fashionapp.utils.Prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailProductFragment : Fragment() {
    lateinit var databinding: FragmentDetailProductBinding
    val viewmodel: DetailProductViewmodel by viewModels()
    val args: DetailProductFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = FragmentDetailProductBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = this@DetailProductFragment.viewmodel.apply {
                idProduct = args.idProduct
            }
        }
        return databinding.root
    }

    override fun onResume() {
        super.onResume()
        viewmodel.getProductById()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpEvent()
        setUpViewPager()
        setUpUIFollowRole()
    }

    private fun setUpViewPager() {
        viewmodel.product.observe(viewLifecycleOwner, Observer {
            val pagerAdapter: ProductViewpage = ProductViewpage(requireContext(), it.image ?: listOf())
            databinding.viewpager.adapter = pagerAdapter
            databinding.viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            databinding.dotIndicator.setViewPager2(databinding.viewpager)
        })
    }

    private fun setUpEvent() {
        viewmodel.eventBack.observe(viewLifecycleOwner, EventObserver {
            findNavController().popBackStack()
        })

        viewmodel.messageEvent.observe(viewLifecycleOwner, EventObserver{
            val action = ChatFragmentDirections.actionGlobalChatFragment(it.id ?: 0, it.name ?: "")
            findNavController().navigate(action)
        })

        viewmodel.goToSellerShopEvent.observe(viewLifecycleOwner, EventObserver{
            val action = DetailProductFragmentDirections.actionGlobalSellerShopFragment(it.id ?: 0)
            findNavController().navigate(action)
        })

        viewmodel.goToEditProductEvent.observe(viewLifecycleOwner, EventObserver{
            val action = AddProductFragmentDirections.actionGlobalAddProductFragment(it)
            findNavController().navigate(action)
        })
    }

    private fun setUpUIFollowRole() {
        val isSeller = Prefs.newInstance(requireContext()).getRole() == Role.SELLER
        databinding.llAdjust.isVisible = !isSeller
        databinding.llBottom.isVisible = !isSeller
        databinding.infoProductSeller.isVisible = isSeller
        databinding.ivEdit.isVisible = isSeller
    }
}