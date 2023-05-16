package com.example.fashionapp.data.local

import com.example.fashionapp.model.CartModel
import com.example.fashionapp.model.Product
import javax.inject.Inject

class MyResponsitory @Inject constructor(
    private val myDAO: MyDAO
) {
    suspend fun getAllCart() = myDAO.getAllCart()

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
}