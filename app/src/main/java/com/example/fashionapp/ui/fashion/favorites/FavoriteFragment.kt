package com.example.fashionapp.ui.fashion.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fashionapp.Define
import com.example.fashionapp.Role
import com.example.fashionapp.adapter.ChatListApdater
import com.example.fashionapp.data.remote.response.ChatListResponse
import com.example.fashionapp.databinding.FragmentFavoriteBinding
import com.example.fashionapp.model.Product
import com.example.fashionapp.ui.fashion.FashionViewmodel
import com.example.fashionapp.utils.Event
import com.example.fashionapp.utils.EventObserver
import com.example.fashionapp.utils.Prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment: Fragment(),ChatListApdater.ItemClickEvent {
     lateinit var databinding : FragmentFavoriteBinding
     val viewmodel: FavoriteViewmodel by viewModels()
     val fashionViewmodel_ by viewModels<FashionViewmodel>(ownerProducer = { requireParentFragment().requireParentFragment() })

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
        viewmodel.getListChat()
        setUpEvent()
    }

    private fun setUpEvent() {
        viewmodel.listChat.observe(viewLifecycleOwner, EventObserver{
            databinding.recycleview.apply {
                adapter = ChatListApdater(requireContext(), it).apply {
                    passValueItemClickEvent(this@FavoriteFragment)
                }
                layoutManager = LinearLayoutManager(requireContext())
            }
        })

        viewmodel.chatResponse.observe(viewLifecycleOwner, EventObserver{
            if (Prefs.newInstance(requireContext()).getRole() == Role.SELLER){
                val action = FavoriteFragmentDirections.actionGlobalChatFragment(
                    it.idUser?: 0,
                    it.name?: "")
                findNavController().navigate(action)
            } else {
                fashionViewmodel_.goToChatDetail(it)
            }

        })
    }

    override fun goToProductDetail(chatMessageResponse: ChatListResponse) {
        viewmodel.chatResponse.value = Event(chatMessageResponse)
    }

}