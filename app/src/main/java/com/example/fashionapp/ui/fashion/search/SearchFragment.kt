package com.example.fashionapp.ui.fashion.search

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
import com.example.fashionapp.databinding.FragmentSearchBinding
import com.example.fashionapp.model.Product
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment: Fragment() {
    lateinit var databinding: FragmentSearchBinding
    val viewmodel: SearchViewmodel by viewModels()
    val navArgs : SearchFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.idSeller = navArgs.id
        viewmodel.loadListProduct()
        setUpEvent()
    }

    private fun setUpEvent() {
        viewmodel.listProduct.observe(viewLifecycleOwner, Observer{
            val adapter = ListProductAdapter(requireContext(), it).apply {
                itemClickEvent = object : ListProductAdapter.ItemClickEvent{
                    override fun goToProductDetail(product: Product) {
                        val action = SearchFragmentDirections.actionGlobalDetailProductFragment(product._id ?: 0)
                        findNavController().navigate(action)
                    }
                }
            }
            databinding.rvSearch.adapter = adapter
            databinding.rvSearch.layoutManager = LinearLayoutManager(requireContext())
        })


        databinding.svProduct.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (!p0.isNullOrBlank()){
                    filter(p0)
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (!p0.isNullOrBlank()){
                    filter(p0)
                }
                return true
            }
        })
    }

    fun filter(text: String) {
        viewmodel.listProduct.value = viewmodel.fullList.filter { it.name?.lowercase()?.contains(text.lowercase()) ?: false }
    }
}