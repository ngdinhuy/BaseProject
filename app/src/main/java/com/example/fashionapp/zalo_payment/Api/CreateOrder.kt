package com.example.fashionapp.zalo_payment.Api

import com.example.fashionapp.zalo_payment.Constant.AppInfo
import kotlin.Throws
import org.json.JSONObject
import com.example.fashionapp.zalo_payment.helper.Helpers
import okhttp3.FormBody
import okhttp3.RequestBody
import java.lang.Exception
import java.util.*

class CreateOrder {
    private inner class CreateOrderData constructor(amount: String) {
        var AppId: String
        var AppUser: String
        var AppTime: String
        var Amount: String
        var AppTransId: String
        var EmbedData: String
        var Items: String
        var BankCode: String
        var Description: String
        var Mac: String

        init {
            val appTime = Date().time
            AppId = java.lang.String.valueOf(AppInfo.APP_ID)
            AppUser = "Android_Demo"
            AppTime = appTime.toString()
            Amount = amount
            AppTransId = Helpers.appTransId
            EmbedData = "{}"
            Items = "[]"
            BankCode = "zalopayapp"
            Description = "Merchant pay for order #" + Helpers.appTransId
            val inputHMac = String.format(
                "%s|%s|%s|%s|%s|%s|%s",
                AppId,
                AppTransId,
                AppUser,
                Amount,
                AppTime,
                EmbedData,
                Items
            )
            Mac = Helpers.getMac(AppInfo.MAC_KEY, inputHMac)
        }
    }

    @Throws(Exception::class)
    fun createOrder(amount: String): JSONObject {
        val input = CreateOrderData(amount)
        val formBody: RequestBody = FormBody.Builder()
            .add("app_id", input.AppId)
            .add("app_user", input.AppUser)
            .add("app_time", input.AppTime)
            .add("amount", input.Amount)
            .add("app_trans_id", input.AppTransId)
            .add("embed_data", input.EmbedData)
            .add("item", input.Items)
            .add("bank_code", input.BankCode)
            .add("description", input.Description)
            .add("mac", input.Mac)
            .build()
        return HttpProvider.sendPost(AppInfo.URL_CREATE_ORDER, formBody)!!
    }
}