package com.example.fashionapp.ui.fashion.chat

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionapp.Define
import com.example.fashionapp.data.remote.response.ChatDetailResponse
import com.example.fashionapp.databinding.FragmentChatBinding
import com.example.fashionapp.utils.Prefs
import com.google.gson.JsonObject
import com.paypal.pyplcheckout.ui.feature.sca.runOnUiThread
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import okhttp3.OkHttpClient
import org.json.JSONObject

@AndroidEntryPoint
class ChatFragment : Fragment() {
    lateinit var databinding: FragmentChatBinding
    val viewmodel: ChatViewmodel by viewModels()
    val navArgs: ChatFragmentArgs by navArgs()
    lateinit var adapter: ChatAdapter
    var mSocket: Socket? = null
    var room = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun connectSocket() {
        try {
            val myID = Prefs.newInstance(requireContext()).getId()
            room = if (navArgs.idPartner > myID){
                "$myID${navArgs.idPartner}"
            } else {
                "${navArgs.idPartner}$myID"
            }

            val options = IO.Options().apply {
                forceNew = true
                reconnectionAttempts = Integer.MAX_VALUE
                reconnection = true
                query = "room=$room"
            }

            mSocket = IO.socket(Define.SOCKET_URL, options)

            mSocket?.on(Socket.EVENT_CONNECT){
                Log.e("Socket: ","Connect succees: room=$room")
            }

            mSocket?.on(Socket.EVENT_DISCONNECT){
                Log.e("Socket: ", "Disconnect")
            }

            mSocket?.on(Socket.EVENT_CONNECT_ERROR){
                Log.e("Socket: ", "Error ${it[0].toString()}")
            }

            mSocket?.on(Define.RECEIVE_MESSAGE_EVENT, Emitter.Listener {
                val json = it[0] as? JsonObject
                Log.e("Socket event:", json.toString())
                //cap nhat sau khi nhan tin nhan
                reloadData()
            })
            mSocket?.connect()
        } catch (e:Exception){
            Log.e("Socket error", e.message.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = FragmentChatBinding.inflate(inflater, container, false).apply {
            viewmodel = this@ChatFragment.viewmodel.apply {
                idPartner = navArgs.idPartner
            }
            namePartner = navArgs.namePartner
            lifecycleOwner = viewLifecycleOwner
        }
        return databinding.root
    }

    override fun onResume() {
        super.onResume()
        viewmodel.loadMessage()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectSocket()
        setUpAdapter()
        initScrollListener()
        setUpEvent()
    }

    private fun initScrollListener() {
        databinding.rlChat.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?

                if (linearLayoutManager != null && viewmodel.listMessage.size > 9 && viewmodel.listMessage.size %10 == 0 && linearLayoutManager.findFirstVisibleItemPosition() == 0) {
                    //bottom of list!
                    loadMore()
                }
            }
        })
    }

    fun loadMore(){
        val list = ArrayList<ChatDetailResponse>(viewmodel.listMessage)
        viewmodel.listMessageDetail.value = list
        viewmodel.loadMessage()
    }

    private fun setUpAdapter() {
        adapter = ChatAdapter(requireContext(), ArrayList()).apply {

        }
        databinding.rlChat.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ChatFragment.adapter
        }
    }

    private fun setUpEvent() {
        databinding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewmodel.listMessageDetail.observe(viewLifecycleOwner, Observer {
            adapter.listChatMessage = it
            adapter.notifyDataSetChanged()
        })

        databinding.ivSend.setOnClickListener {
            val jsonData = JSONObject().apply {
                put("senderName", Prefs.newInstance(requireContext()).getId())
                put("targetUserName", navArgs.idPartner)
                put("message", databinding.etMessage.text.toString())
                put("room", room)
            }
            mSocket?.emit(Define.RECEIVE_MESSAGE_EVENT, jsonData)
            databinding.etMessage.setText("")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mSocket?.disconnect()
        mSocket?.off("get_message")
    }

    //su dung khi gui hoac nhan tin
    fun reloadData(){
        viewmodel.offset = 0
        viewmodel.listMessage = java.util.ArrayList()
        viewmodel.loadMessage()
        databinding.rlChat.smoothScrollToPosition(viewmodel.listMessageDetail.value!!.size)
    }
}