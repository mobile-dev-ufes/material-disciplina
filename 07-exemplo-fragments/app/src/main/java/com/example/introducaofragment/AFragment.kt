package com.example.introducaofragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class AFragment : Fragment(R.layout.fragment_a) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prodId = requireArguments().getInt("PROD_ID")
        val prodName = requireArguments().getString("PROD_NAME")

        Log.i("FRAG_A", "Product ID: $prodId")
        Log.i("FRAG_A", "Product Name: $prodName")

        // Apenas ilustrando o acesso ao contexto
        val contexto = context
    }
}