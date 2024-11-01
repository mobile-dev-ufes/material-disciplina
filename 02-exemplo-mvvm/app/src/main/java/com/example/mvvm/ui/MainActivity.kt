package com.example.mvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.viewModel.MainViewModel
import com.example.mvvm.R
import com.example.mvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainVM: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainVM = ViewModelProvider(this).get(MainViewModel::class.java)
        setObserver()
        binding.buttonRegister.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.button_register) {
            val idProduct = binding.editIdProduto.text.toString()
            val nameProduct = binding.editProductName.text.toString()

            mainVM.registerProduct(idProduct, nameProduct)
        }
    }

    private fun setObserver() {
        mainVM.isRegistered().observe(this, Observer {
            if(it){
                Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, R.string.register_fail, Toast.LENGTH_SHORT).show()
            }
        })
    }

}