package com.example.mvvm.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm.repository.ProductRepository

class MainViewModel : ViewModel() {

    private var register = MutableLiveData<Boolean>()
    private val productRepository = ProductRepository()

    fun isRegistered(): LiveData<Boolean> {
        return register
    }

    fun registerProduct(idProd: String, nameProd: String) {
        register.value = productRepository.register(idProd, nameProd)
    }

}