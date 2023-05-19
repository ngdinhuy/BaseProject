package com.example.fashionapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fashionapp.model.BillModel
import com.example.fashionapp.model.CartModel

@Dao
interface MyDAO {
    @Query("Select * from cart where userName = :userName AND idBill = :idBillModel")
    fun getAllCart(userName: String, idBillModel: Int): List<CartModel>

    @Insert
    fun insertCart(cartModel: CartModel)

    @Query("SELECT EXISTS (SELECT * FROM cart WHERE idProduct = :idProduct AND userName = :userName AND idBill= 0)")
    fun checkProductExist(idProduct:Int, userName: String): Boolean

    @Query("UPDATE cart SET quantity = :quantity WHERE idProduct = :idProduct AND idBill=0")
    fun updateQuantityCart(idProduct: Int, quantity:Int)

    @Query("SELECT quantity FROM cart WHERE idProduct=:idProduct AND userName=:userName AND idBill=0")
    fun getQuantityProduct(idProduct: Int, userName: String):Int

    @Query("DELETE FROM cart WHERE idProduct=:idProduct AND userName=:userName AND idBill=0")
    fun deleteItemCart(idProduct: Int, userName: String)

    @Query("SELECT * FROM bill WHERE username = :userName")
    fun getAllBill(userName: String): List<BillModel>

    @Query("SELECT COUNT(id) FROM bill WHERE username = :username")
    fun getAmountill(username: String):Int

    @Insert
    fun addBill(billModel: BillModel)

    @Query("UPDATE cart SET idBill= :idBill WHERE userName = :username")
    fun checkout(idBill: Int, username: String)
}