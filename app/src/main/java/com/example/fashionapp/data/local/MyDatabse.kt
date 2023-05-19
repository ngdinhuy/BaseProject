package com.example.fashionapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fashionapp.model.BillModel
import com.example.fashionapp.model.CartModel

@Database(
    entities = [CartModel::class, BillModel::class]
    , version = 1
)
abstract class MyDatabse: RoomDatabase() {
    abstract fun getMyDAO(): MyDAO
}