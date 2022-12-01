package com.example.exemploroommvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.exemploroommvvm.R
import com.example.exemploroommvvm.data.model.ProductModel
import com.example.exemploroommvvm.databinding.ActivityAddProductBinding
import com.example.exemploroommvvm.utils.Constants
import com.example.exemploroommvvm.viewModel.AddProductViewModel

class AddProductActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityAddProductBinding
    private lateinit var addProdVM: AddProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.imageBack.setOnClickListener(this)
        binding.buttonRegister.setOnClickListener(this)

        addProdVM = ViewModelProvider(this).get(AddProductViewModel::class.java)
        setObserver()
    }

    override fun onClick(view: View) {
        if (view.id == R.id.image_back){
            finish()
        } else if (view.id == R.id.button_register) {

            try {
                val p = ProductModel().apply {
                    this.name = binding.editProdName.text.toString()
                    this.prodCode = binding.editProdCode.text.toString()
                    this.amount = binding.editProdAmount.text.toString().toInt()
                    this.price = binding.editProdPrice.text.toString().toFloat()
                }
                if (p.name == ""){
                    Toast.makeText(this, R.string.empty_name_msg, Toast.LENGTH_SHORT).show()
                } else if (p.prodCode == "") {
                    Toast.makeText(this, R.string.empty_prod_code_msg, Toast.LENGTH_SHORT).show()
                }

                addProdVM.saveProduct(p)

            } catch (e: NumberFormatException){
                Toast.makeText(this, R.string.empty_number_msg, Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun clearEdits(){
        binding.editProdName.getText().clear()
        binding.editProdCode.getText().clear()
        binding.editProdAmount.getText().clear()
        binding.editProdPrice.getText().clear()
    }

    private fun setObserver(){
        addProdVM.getIsSaved().observe(this, Observer {
            if (it == Constants.BD_MSGS.SUCCESS) {
                Toast.makeText(this, R.string.success_add, Toast.LENGTH_SHORT).show()
                clearEdits()
            } else if (it == Constants.BD_MSGS.FAIL) {
                Toast.makeText(this, R.string.fail_add, Toast.LENGTH_SHORT).show()
            } else if (it == Constants.BD_MSGS.CONSTRAINT) {
                Toast.makeText(this, R.string.constraint_add, Toast.LENGTH_SHORT).show()
            }
        })
    }
}