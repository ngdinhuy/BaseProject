package com.example.fashionapp

import android.Manifest
import com.example.fashionapp.model.Product

object Define {
    const val BASE_URL = "http://192.168.1.15:8080"
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

    //paypal pay
    const val CLIENT_KEY = "AWC3WiwnQ4HQAmvLqlXehCbmG4S0PTnZ1_7BpuDMQ5r595i0-HGEesLHw1j0gSK7_VH-sepTyyEbQA19"

    const val SPAN_COUNT_GRIDLAYOUT_CATEGORY = 2

    const val HTTP = "http"

    val listFilterText = listOf("","Customer review", "Price: lowest to high", "Price: highest to low")

    val listPermission = listOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    val listMonthOfYear = listOf<String>(
        "1/2023","2/2023","3/2023", "4/2023", "5/2023", "6/2023", "7/2023", "8/2023", "9/2023", "10/2023", "11/2023", "12/2023"
    )
}
object Role{
    const val ADMIN = 0
    const val SELLER = 1
    const val CUSTOMER = 2
}

object StatisticType {
    const val ORDER_CURRENT_MONTH = 1
    const val RATE_CURRENT_MONTH = 2
}
