package com.example.exemploroommvvm.viewModel

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.exemploroommvvm.data.model.ProductModel
import com.example.exemploroommvvm.data.room.AppDatabase
import com.example.exemploroommvvm.utils.Constants

class AddProductViewModel(application: Application) : AndroidViewModel(application) {

    private var savedMsg = MutableLiveData<Int>()

    fun getIsSaved(): LiveData<Int> {
        return savedMsg
    }

    fun saveProduct(p: ProductModel){
        val db = AppDatabase.getDatabase(getApplication()).ProductDAO()
        var resp = 0L
        try {
            resp = db.insert(p)
            savedMsg.value = if(resp > 0) Constants.BD_MSGS.SUCCESS else Constants.BD_MSGS.FAIL
        } catch (e: SQLiteConstraintException){
            savedMsg.value = Constants.BD_MSGS.CONSTRAINT
        }

    }
}