package com.devmob.introfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.devmob.introfirebase.databinding.WelcomeMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: WelcomeMainBinding
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private var userName = "Usuário"
    private var userLastName = ""
    private var userUid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WelcomeMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userUid = auth.currentUser?.uid
        readUserData()

        binding.buttonLogout.setOnClickListener({
            // Deslogando
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        })
    }

    private fun readUserData(){
        db.collection("usuarios").document(userUid!!)
            .get()
            .addOnSuccessListener({
                if (it != null) {
                    userName = it.getString("name")!!
                    userLastName = it.getString("lastName")!!
                }
                binding.textNameLastName.text = "$userName $userLastName"
            })
            .addOnFailureListener({
                Log.d("Firebase", "Problema ao ler: ", it)
            })
    }
}