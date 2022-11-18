package com.example.exemplo_room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.exemplo_room.database.AppDatabase
import com.example.exemplo_room.model.ProductModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testBD()
    }

    fun testBD() {
        val db = AppDatabase.getDatabase(this).ProductDAO()
        val prod = ProductModel().apply{
            this.id = 0
            this.name = "Smartphone"
            this.price = 999f
            this.isAvailable = false
        }

        if(db.insert(prod)>0){
            Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Derrota", Toast.LENGTH_SHORT).show()
        }

//        val p = db.getAvailables(0)
//        Toast.makeText(this, p[0].name, Toast.LENGTH_SHORT).show()


    }
}