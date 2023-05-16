package com.example.fashionapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fashionapp.model.CartModel

@Dao
interface MyDAO {
    @Query("Select * from cart")
    fun getAllCart(): List<CartModel>

    @Insert
    fun insertCart(cartModel: CartModel)

    @Query("SELECT EXISTS (SELECT * FROM cart WHERE idProduct = :idProduct AND userName = :userName)")
    fun checkProductExist(idProduct:Int, userName: String): Boolean

    @Query("UPDATE cart SET quantity = :quantity WHERE idProduct = :idProduct")
    fun updateQuantityCart(idProduct: Int, quantity:Int)

    @Query("SELECT quantity FROM cart WHERE idProduct=:idProduct AND userName=:userName")
    fun getQuantityProduct(idProduct: Int, userName: String):Int

    @Query("DELETE FROM cart WHERE idProduct=:idProduct AND userName=:userName")
    fun deleteItemCart(idProduct: Int, userName: String)
}