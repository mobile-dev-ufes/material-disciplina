package com.example.exemploroommvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exemploroommvvm.R
import com.example.exemploroommvvm.databinding.ActivityListProductBinding
import com.example.exemploroommvvm.utils.Constants
import com.example.exemploroommvvm.view.adapter.ListProductAdapter
import com.example.exemploroommvvm.viewModel.ListProductViewModel

class ListProductActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityListProductBinding
    lateinit var listVM: ListProductViewModel
    private val adapter = ListProductAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.imageBack.setOnClickListener(this)

        // layout
        binding.recyclerListProducts.layoutManager = LinearLayoutManager(this)

        // Adapter
        binding.recyclerListProducts.adapter = adapter

        listVM = ViewModelProvider(this).get(ListProductViewModel::class.java)

        listVM.getAllProducts()

        setObserver()

    }

    override fun onClick(view: View) {
        if (view.id == R.id.image_back) {
            finish()
        }
    }

    fun setObserver() {
        listVM.getListMsg().observe(this, Observer {
            if (it == Constants.BD_MSGS.SUCCESS){
                Toast.makeText(this, R.string.success_search, Toast.LENGTH_SHORT).show()
            } else if (it == Constants.BD_MSGS.NOT_FOUND){
                Toast.makeText(this, R.string.not_found_search, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, R.string.fail_search, Toast.LENGTH_SHORT).show()
            }
        })

        listVM.getProdList().observe(this, Observer {
            adapter.updateProdList(it)
        })
    }
}