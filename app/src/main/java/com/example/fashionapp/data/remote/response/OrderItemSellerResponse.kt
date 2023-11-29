package com.example.fashionapp.data.remote.response

import com.example.fashionapp.StatusPayment

class OrderItemSellerResponse(
    var idOrder: Int? = 0,
    var statePayment: Int? = 0,
    var idProduct: Int? = 0,
    var nameProduct: String? = "",
    var priceProduct: Double? = 0.0,
    var idUSer: Int? = 0,
    var quantity: Int? = 0,
    var nameUser: String? = "",
    var totalPrice: Double? = 0.0,
    var images: List<String>? = listOf()
) {
    fun displayStatePayment(): String = if (statePayment == StatusPayment.PAYED) "Status: paid" else "Status: Unpaid"

    fun dispalyPaid() : Boolean = statePayment == StatusPayment.PAYED

}
