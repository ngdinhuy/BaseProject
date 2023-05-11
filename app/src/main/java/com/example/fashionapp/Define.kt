package com.example.fashionapp

import com.example.fashionapp.model.Product

object Define {
    const val BASE_URL = "https://fakestoreapi.com/"
    const val MENS_QUOTES = "Fashions fade, style is eternal."
    const val WOMEN_QUOTES = "Elegance is elimination."

    //categories
    const val MEN_CLOTHES = "men's clothing"
    const val WOMEN_CLOTHES = "women's clothing"
    const val JEWELERY = "jewelery"
    const val ELECTRONICS = "electronics"

    var listProduct = listOf<Product>()

    const val CART_TABLE = "cart"
}