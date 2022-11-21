package com.example.exemploroommvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.exemploroommvvm.R
import com.example.exemploroommvvm.databinding.ActivityVisualizeProductBinding
import com.example.exemploroommvvm.utils.Constants
import com.example.exemploroommvvm.viewModel.VisualizeProductViewModel

class VisualizeProductActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityVisualizeProductBinding
    lateinit var visVM: VisualizeProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVisualizeProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.imageBack.setOnClickListener(this)
        binding.buttonSearch.setOnClickListener(this)

        visVM = ViewModelProvider(this).get(VisualizeProductViewModel::class.java)
        setObserver()
    }

    override fun onClick(view: View) {
        if (view.id == R.id.image_back){
            finish()
        } else if (view.id == R.id.button_search) {

            val cod = binding.editProdCode.text.toString()
            if (cod == "") {
                Toast.makeText(this, R.string.empty_prod_code_msg, Toast.LENGTH_SHORT).show()
            } else {
                visVM.searchByProduct(cod)
            }
        }
    }

    private fun clearTexts(){
        binding.textProdNameAnswer.text = ""
        binding.textProdPriceAnswer.text = ""
        binding.textProdAmountAnswer.text = ""
    }

    fun setObserver(){
        visVM.getSearchMsg().observe(this, Observer {
            if (it == Constants.BD_MSGS.SUCCESS){
                Toast.makeText(this, R.string.success_search, Toast.LENGTH_SHORT).show()
            } else if (it == Constants.BD_MSGS.NOT_FOUND){
                Toast.makeText(this, R.string.not_found_search, Toast.LENGTH_SHORT).show()
                clearTexts()
            } else {
                Toast.makeText(this, R.string.fail_search, Toast.LENGTH_SHORT).show()
                clearTexts()
            }
        })

        visVM.getProd().observe(this, Observer {
            binding.textProdNameAnswer.text = it.name
            binding.textProdPriceAnswer.text = it.price.toString()
            binding.textProdAmountAnswer.text = it.amount.toString()
        })
    }

}