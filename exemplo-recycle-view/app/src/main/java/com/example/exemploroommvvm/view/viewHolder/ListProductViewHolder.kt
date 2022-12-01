package com.example.exemploroommvvm.view.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.exemploroommvvm.data.model.ProductModel
import com.example.exemploroommvvm.databinding.ProductLineBinding

class ListProductViewHolder(private val binding: ProductLineBinding) : RecyclerView.ViewHolder(binding.root) {


    fun bindVH(prod: ProductModel){
        binding.textProdNameAnswer.text = prod.name
        binding.textProdPriceAnswer.text = prod.price.toString()
        binding.textProdAmountAnswer.text = prod.amount.toString()
    }

}