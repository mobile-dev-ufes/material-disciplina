package com.example.exemploroommvvm.view.listener

import com.example.exemploroommvvm.data.model.ProductModel

interface OnProductListener {
    fun onClick(p: ProductModel)
}