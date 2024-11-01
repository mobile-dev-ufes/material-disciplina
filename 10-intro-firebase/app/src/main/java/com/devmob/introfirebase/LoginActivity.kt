package com.devmob.introfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.devmob.introfirebase.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textRegister.setOnClickListener({
            startActivity(Intent(this, RegisterActivity::class.java))
        })

        handleLogin()
    }

    private fun handleLogin(){

        binding.buttonLogin.setOnClickListener({
            val email = binding.editTextEmail.text.toString()
            val passwd = binding.editTextPasswd.text.toString()
            if (email.isEmpty()) {
                Toast.makeText(this, "Preencha seu email", Toast.LENGTH_SHORT).show()
            } else if (passwd.isEmpty()){
                Toast.makeText(this, "Preencha sua senha", Toast.LENGTH_SHORT).show()
            } else {

                auth.signInWithEmailAndPassword(email, passwd).addOnCompleteListener({
                    if (it.isSuccessful){
                        startActivity(Intent(this, WelcomeActivity::class.java))
                        finish()
                    }
                }).addOnFailureListener({
                    var msg = "Houve algum problema no login, tente novamente"
                    if (it is FirebaseAuthInvalidUserException) {
                        msg = "Este usuário não existe"
                    } else if (it is FirebaseAuthInvalidCredentialsException) {
                        msg = "O usuário existe mas a senha está incorreta"
                    } else if (it is FirebaseNetworkException) {
                        msg = "Falha ao conectar com a internet"
                    }
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                })
            }
        })
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }
    }

}