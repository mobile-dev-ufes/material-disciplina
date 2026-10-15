package com.example.contador_habitos.presentation

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import android.widget.TextView
import com.example.contador_habitos.R

class MainActivity : Activity() {

    private lateinit var txtCounter: TextView
    private lateinit var btnAdd: Button
    private lateinit var btnReset: Button

    private var counter = 0

    // Constantes para o SharedPreferences
    private val PREFS_NAME = "wear_prefs"
    private val KEY_COUNTER = "counter"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Linkando com os elementos do Layout. Poderia ser feito com View Binding também
        txtCounter = findViewById(R.id.txtCounter)
        btnAdd = findViewById(R.id.btnAdd)
        btnReset = findViewById(R.id.btnReset)

        carregarContador()
        atualizarUI()

        // Implementação do evento do clique para adicionar +1
        btnAdd.setOnClickListener {
            counter++
            vibrar()
            atualizarUI()
        }

        // Implementação do evento do clique para resetar o contador
        btnReset.setOnClickListener {
            counter = 0
            vibrar()
            atualizarUI()
        }
    }

    // Quando entrar em pausa, o contador é salvo no SharedPreferences
    override fun onPause() {
        super.onPause()
        salvarContador()
    }

    // Basicamente atualiza o valor na tela. Fácil de adaptar para MVVM
    private fun atualizarUI() {
        txtCounter.text = counter.toString()
    }

    // Método para salvar o valor do contador no SharedPreferences
    private fun salvarContador() {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        prefs.edit()
            .putInt(KEY_COUNTER, counter)
            .apply()
    }

    // Quando iniciar ou voltar de uma pausa, recarrega o valor do contador
    // Se não existir, começa do zero
    private fun carregarContador() {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        counter = prefs.getInt(KEY_COUNTER, 0)
    }

    // Método para realizar a vibração do aparelho (feedback tátil)
    // Importante: é necessário pedir permissão no Manifest para executar uma vibração
    private fun vibrar() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    50,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        }
    }
}
