package com.example.fashionapp.ui.fashion.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fashionapp.Define
import com.example.fashionapp.adapter.FavoritesAdapter
import com.example.fashionapp.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment: Fragment() {
     lateinit var databinding : FragmentFavoriteBinding
     val viewmodel: FavoriteViewmodel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databinding.recycleview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = FavoritesAdapter(requireContext(), Define.listLikeItem)
        }
    }

}