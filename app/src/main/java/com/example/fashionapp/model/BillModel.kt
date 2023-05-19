package com.example.fashionapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fashionapp.Define

@Entity(tableName = Define.BILL_TABLE)
data class BillModel (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var _id : Int?,
            var quantity:Int?,
            var date:String?,
            var total:Int?,
            var username: String?
)