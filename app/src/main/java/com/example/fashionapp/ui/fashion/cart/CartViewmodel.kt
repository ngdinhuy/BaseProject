package com.example.fashionapp.ui.fashion.cart

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.Define
import com.example.fashionapp.adapter.CartAdapter
import com.example.fashionapp.data.local.MyResponsitory
import com.example.fashionapp.model.BillModel
import com.example.fashionapp.model.CartModel
import com.example.fashionapp.utils.dialog.CustomDialog
import com.example.fashionapp.utils.Event
import com.example.fashionapp.utils.Prefs
import com.example.fashionapp.zalo_payment.Api.CreateOrder
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@HiltViewModel
class CartViewmodel @Inject constructor(
    val responsitoryImpl: ShopAppResponsitoryImpl,
    val myResponsitory: MyResponsitory,
    @ApplicationContext val context: Context,
) : ViewModel(), CartAdapter.ClickEvent, CustomDialog.ClickPayEvent {
    private val _listCard = MutableLiveData<Event<List<CartModel>>>()
    val listCard: LiveData<Event<List<CartModel>>> = _listCard
    private val _clickCheckoutEvent = MutableLiveData<Event<Unit>>()
    val clickCheckoutEvent : LiveData<Event<Unit>> = _clickCheckoutEvent
    private val _clickPayZalo = MutableLiveData<Event<Unit>>()
    val clickPayZalo: LiveData<Event<Unit>> = _clickPayZalo
    val totalPrice = MutableLiveData<Double>(0.0)


    fun getAllCart() {
        viewModelScope.launch(Dispatchers.IO) {
            val username = Prefs.newInstance(context).getUsername()
            val listCart = myResponsitory.getAllCart(username!!, idBill = Define.ITEM_NOT_CHECKOUT_INDEX)
            _listCard.postValue(Event(listCart))
            var total = 0.0
            listCart.forEach {
                total += (it.quantity!! * Define.listProduct[it.idProduct!!-1].price!!)
            }
            totalPrice.postValue(total)
        }
    }

    fun deleteItem(idProduct: Int){
        val username = Prefs.newInstance(context).getUsername()
        viewModelScope.launch(Dispatchers.IO) {
            myResponsitory.deleteItemInCart(idProduct, username)
        }
    }

    override fun addQuantity(idProduct: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            myResponsitory.addProduct(Define.listProduct[idProduct-1],
                Prefs.newInstance(context = context).getUsername()!!, 1)
            totalPrice.postValue(totalPrice.value!! + Define.listProduct[idProduct-1].price!!)
        }
    }

    override fun subtractQuantity(idProduct: Int, currentQuantity: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            myResponsitory.subtractOneQuantity(idProduct, Prefs.newInstance(context = context).getUsername()!!)
            totalPrice.postValue(totalPrice.value!! - Define.listProduct[idProduct-1].price!!)
        }
    }

    fun clickCheckout(){
        _clickCheckoutEvent.value = Event(Unit)
    }

    fun checkout(){
        viewModelScope.launch(Dispatchers.IO) {
            val username = Prefs.newInstance(context).getUsername()
            val listCartModel = myResponsitory.getAllCart(username!!, 0)
            var total = 0
            var quantity = 0
            listCartModel.forEach {
                if (it.price!=null && it.quantity!=null){
                    total += it.price!!.toDouble().toInt() * it.quantity!!
                    quantity = it.quantity!!
                }
            }
            val currentTime = Calendar.getInstance().time
            val sdf = SimpleDateFormat("yyyy-MM-dd")

            myResponsitory.addBill(BillModel(null,quantity,sdf.format(currentTime),total,username))
            val countBill = myResponsitory.getAnmountBill(Prefs.newInstance(context).getUsername()!!)
            myResponsitory.checkout(countBill+1, Prefs.newInstance(context).getUsername()!!)
            _listCard.postValue(Event(listOf()))
        }
    }

    override fun clickZalo() {
        _clickPayZalo.value = Event(Unit)
    }

    override fun clickMomo() {
        checkout()
    }
}