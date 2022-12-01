package com.example.exemploroommvvm.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.exemploroommvvm.data.model.ProductModel
import com.example.exemploroommvvm.data.room.AppDatabase
import com.example.exemploroommvvm.utils.Constants
import java.lang.Exception

class ListProductViewModel(application: Application) : AndroidViewModel(application) {

    private var listMsg = MutableLiveData<Int>()
    private var prodList = MutableLiveData<List<ProductModel>>()

    fun getListMsg(): LiveData<Int> {
        return listMsg
    }

    fun getProdList(): LiveData<List<ProductModel>> {
        return prodList
    }

    fun getAllProducts() {
        val db = AppDatabase.getDatabase(getApplication()).ProductDAO()
        try {
            val resp = db.getAllProducts()
            if (resp == null) {
                listMsg.value = Constants.BD_MSGS.NOT_FOUND
            } else {
                listMsg.value = Constants.BD_MSGS.SUCCESS
                prodList.value = resp
            }
        } catch (e: Exception) {
            listMsg.value = Constants.BD_MSGS.FAIL
        }
    }


}