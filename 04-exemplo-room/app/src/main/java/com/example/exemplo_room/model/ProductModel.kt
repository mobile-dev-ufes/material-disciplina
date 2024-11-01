package com.example.exemplo_room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Product")
class ProductModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Int = 0

    @ColumnInfo(name="name")
    var name: String = ""

    @ColumnInfo(name="price")
    var price: Float = 0f

    @ColumnInfo(name="is_available")
    var isAvailable: Boolean = false
}