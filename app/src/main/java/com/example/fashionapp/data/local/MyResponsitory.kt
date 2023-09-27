package com.example.fashionapp.data.local

import com.example.fashionapp.model.BillModel
import com.example.fashionapp.model.CartModel
import com.example.fashionapp.model.Product
import javax.inject.Inject

class MyResponsitory @Inject constructor(
    private val myDAO: MyDAO
) {
    suspend fun getAllCart(username: String, idBill: Int) = myDAO.getAllCart(username, idBill)

    suspend fun insertCart(cartModel: CartModel) = myDAO.insertCart(cartModel)

    suspend fun addProduct(product: Product, username: String, quantity: Int) {
        if (myDAO.checkProductExist(product._id!!, username)) {
            addQuantity(idProduct = product._id, username, quantityAdd = quantity)
        } else {
            myDAO.insertCart(
                CartModel(
                    null,
                    userName = username,
                    idProduct = product._id,
                    idBill = 0,
                    quantity = quantity,
                    price = product.price.toString()
                )
            )
        }
    }

    suspend fun addQuantity(idProduct: Int, username: String, quantityAdd: Int?) {
        val quantity = myDAO.getQuantityProduct(idProduct = idProduct, userName = username)
        quantityAdd?.let {
            myDAO.updateQuantityCart(idProduct, quantity + it)
            return
        }
        myDAO.updateQuantityCart(idProduct, quantity + 1)
    }

    suspend fun subtractOneQuantity(idProduct: Int, username: String) {
        val quantity = myDAO.getQuantityProduct(idProduct = idProduct, userName = username)
        if (quantity == 1) {
            myDAO.deleteItemCart(idProduct, username)
        } else{
            myDAO.updateQuantityCart(idProduct, quantity - 1)
        }
    }

    suspend fun deleteItemInCart(idProduct: Int, username: String){
        myDAO.deleteItemCart(idProduct, username)
    }
    suspend fun checkout(idBill: Int, username: String) = myDAO.checkout(idBill, username)

    suspend fun getAllBill(username: String): List<BillModel> = myDAO.getAllBill(username)

    suspend fun getAnmountBill(username: String): Int = myDAO.getAmountill(username)

    suspend fun addBill(billModel: BillModel) = myDAO.addBill(billModel)

}