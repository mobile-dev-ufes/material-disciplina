package com.example.exemploroommvvm.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.exemploroommvvm.data.model.ProductModel
import com.example.exemploroommvvm.data.room.AppDatabase
import com.example.exemploroommvvm.utils.Constants
import java.lang.Exception
import java.lang.NullPointerException

class VisualizeProductViewModel(application: Application) : AndroidViewModel(application) {

    private var prod = MutableLiveData<ProductModel>()
    private var searchMsg = MutableLiveData<Int>()

    fun getProd(): LiveData<ProductModel> {
        return prod
    }

    fun getSearchMsg(): LiveData<Int> {
        return searchMsg
    }

    fun searchByProduct(code: String) {
        val db = AppDatabase.getDatabase(getApplication()).ProductDAO()
        try {
            val resp = db.getByProdCode(code)
            if (resp == null) {
                searchMsg.value = Constants.BD_MSGS.NOT_FOUND
            } else {
                searchMsg.value = Constants.BD_MSGS.SUCCESS
                prod.value = resp
            }
        } catch (e: Exception) {
            searchMsg.value = Constants.BD_MSGS.FAIL
        }
    }

}