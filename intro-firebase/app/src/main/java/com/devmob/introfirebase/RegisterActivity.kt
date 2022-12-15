package com.devmob.introfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.devmob.introfirebase.databinding.ActivityRegisterBinding
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleRegister()
    }

    private fun handleRegister() {

        binding.buttonRegister.setOnClickListener({
            val name = binding.editTextName.text.toString()
            val lastName = binding.editTextLastName.text.toString()
            val email = binding.editTextEmail.text.toString()
            val passwd1 = binding.editTextPasswd1.text.toString()
            val passwd2 = binding.editTextPasswd2.text.toString()

            if (name.isEmpty()) {
                Toast.makeText(this, "Preencha seu nome", Toast.LENGTH_SHORT).show()
            } else if (lastName.isEmpty()) {
                Toast.makeText(this, "Preencha seu sobrenome", Toast.LENGTH_SHORT).show()
            } else if (email.isEmpty()) {
                Toast.makeText(this, "Preencha seu email", Toast.LENGTH_SHORT).show()
            } else if (passwd1.isEmpty()) {
                Toast.makeText(this, "Preencha a senha 1", Toast.LENGTH_SHORT).show()
            } else if (passwd2.isEmpty()) {
                Toast.makeText(this, "Preencha a senha 2", Toast.LENGTH_SHORT).show()
            } else if (passwd1 != passwd2) {
                Toast.makeText(this, "Senha 1 não pode ser diferente da 2", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(email, passwd1).addOnCompleteListener({
                    if (it.isSuccessful) {
                        saveUserData(name, lastName)
                        Toast.makeText(this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                }).addOnFailureListener({
                    var msg = "Houve algum problema no cadastro"
                    if (it is FirebaseAuthInvalidCredentialsException) {
                        msg = "Digite um email válido"
                    } else if (it is FirebaseAuthWeakPasswordException) {
                        msg = "Digite uma senha com no mínimo 6 caracteres"
                    } else if (it is FirebaseAuthUserCollisionException) {
                        msg = "Este e-mail já foi cadastrado"
                    } else if (it is FirebaseNetworkException) {
                        msg = "Falha ao conectar com a internet"
                    }
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                })
            }
        })
    }

    private fun saveUserData(name: String, lastName: String){
        val userMap = hashMapOf(
            "name" to name,
            "lastName" to lastName
        )
        val currUserId = auth.currentUser?.uid
        if (currUserId != null) {
            db.collection("usuarios").document(currUserId)
                .set(userMap)
                .addOnSuccessListener {
                    Log.d("Firebase", "Salvo com sucesso")
                }
                .addOnFailureListener{
                    Log.d("Firebase", "Problema ao salvar: ", it)
                }
        }
    }

}