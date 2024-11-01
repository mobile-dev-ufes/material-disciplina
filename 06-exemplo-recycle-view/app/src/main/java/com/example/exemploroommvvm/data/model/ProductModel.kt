package com.example.exemploroommvvm.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName="Product", indices = [Index(value = ["prod_code"], unique = true)])
class ProductModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Int = 0

    @ColumnInfo(name="name")
    var name: String = ""

    @ColumnInfo(name="prod_code")
    var prodCode: String = ""

    @ColumnInfo(name="price")
    var price: Float = 0f

    @ColumnInfo(name="amount")
    var amount: Int = 0
}