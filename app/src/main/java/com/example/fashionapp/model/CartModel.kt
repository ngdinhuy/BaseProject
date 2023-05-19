package com.example.fashionapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fashionapp.Define

@Entity(tableName = Define.CART_TABLE)
data class CartModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var userName : String? = null,
    var idProduct: Int? = null,
    var idBill: Int? = null,
    var quantity: Int? = null,
    var price: String? = null
)
