package com.example.fashionapp.ui.fashion.detail_product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.fashionapp.Role
import com.example.fashionapp.databinding.FragmentDetailProductBinding
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
            product = args.product
            viewmodel = this@DetailProductFragment.viewmodel
        }
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpEvent()
        setUpViewPager()
        setUpUIFollowRole()
    }

    private fun setUpViewPager() {
        val pagerAdapter: ProductViewpage = ProductViewpage(requireContext(), args.product?.image ?: listOf())
        databinding.viewpager.adapter = pagerAdapter
        databinding.viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        databinding.dotIndicator.setViewPager2(databinding.viewpager)
    }

    private fun setUpEvent() {
        viewmodel.eventBack.observe(viewLifecycleOwner, EventObserver {
            findNavController().popBackStack()
        })
    }

    private fun setUpUIFollowRole() {
        if (Prefs.newInstance(requireContext()).getRole() == Role.SELLER) {
            databinding.llAdjust.isVisible = false
            databinding.llAddToCart.isVisible = false
        } else {

        }
    }
}