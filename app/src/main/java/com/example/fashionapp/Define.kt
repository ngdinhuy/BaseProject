package com.example.fashionapp

import com.example.fashionapp.model.Product

object Define {
    const val BASE_URL = "http://10.1.30.182:8080"
    const val MENS_QUOTES = "Fashions fade, style is eternal."
    const val WOMEN_QUOTES = "Elegance is elimination."

    //categories
    const val MEN_CLOTHES = "men's clothing"
    const val WOMEN_CLOTHES = "women's clothing"
    const val JEWELERY = "jewelery"
    const val ELECTRONICS = "electronics"

    var listProduct = listOf<Product>()
    var listLikeItem = ArrayList<Int>()

    //sql lite
    const val CART_TABLE = "cart"
    const val BILL_TABLE = "bill"
    const val DATABASE_NAME = "fashion"
    const val ITEM_NOT_CHECKOUT_INDEX = 0

    //zalo pay
    const val APP_ID = 2554
    const val MAC_KEY = "PcY4iZIKFCIdgZvA6ueMcMHHUbRLYjPL"
    const val URL_CREATE_ORDER = "https://sb-openapi.zalopay.vn/v2/create"

    const val SPAN_COUNT_GRIDLAYOUT_CATEGORY = 2

    val listFilterText = listOf("","Customer review", "Price: lowest to high", "Price: highest to low")
}
