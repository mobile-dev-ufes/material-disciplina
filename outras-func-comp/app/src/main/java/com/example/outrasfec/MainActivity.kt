package com.example.outrasfec

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.outrasfec.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        // Código de exemplo da SnackBar
        binding.buttonSnackBar.setOnClickListener {
            val snack = Snackbar.make(binding.mainLayout, "1 item removido", Snackbar.LENGTH_SHORT)
            snack.setBackgroundTint(Color.RED)
            snack.setTextColor(Color.GRAY)
            snack.setTextMaxLines(1)

            snack.setAction("Desfazer", View.OnClickListener {
                Snackbar.make(binding.mainLayout, "Acionar ação de desfazer", Snackbar.LENGTH_SHORT).show()
            })

            snack.show()
        }

        // Código de exemplo para o AlertDialog
        binding.buttonAlertDialog.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Executando uma ação")
                .setMessage("Tem certeza que deseja realizar esta ação?")
                .setPositiveButton("Sim", { dialog, bt ->
                    Log.i("AlertDialog", "Sim")
                })
                .setNegativeButton("Não", { dialog, bt ->
                    Log.i("AlertDialog", "Não")
                })
                .setNeutralButton("Neutro", { dialog, bt ->
                    Log.i("AlertDialog", "Neutro")
                })
                .create()
                .show()
        }

        // Código para o exemplo dos spinners
        setSpinnerMonths()
        val dayStr = binding.spinnerDays.selectedItem
        val dayId = binding.spinnerDays.selectedItemId
        val monthStr = binding.spinnerMonthsAvailable.selectedItem
        val monthId = binding.spinnerMonthsAvailable.selectedItemId

        // Código para a ProgressBar
        binding.buttonVisibility.setOnClickListener {
            binding.progressBarCircle.visibility = View.GONE // para remover o elemento
        }

        binding.buttonIncrement.setOnClickListener {
            binding.progressBarBar.incrementProgressBy(10)
        }

        // Código para o CheckBox e Switch
        val isArgChamp = binding.checkboxArgentina.isChecked
        val isBraChamp = binding.switchBrasil.isChecked

        // Código para o RadioButton
        val isYes = binding.RadioYes.isChecked
        val isNo = binding.RadioNo.isChecked
        val isMaybe = binding.RadioMaybe.isChecked

        // Código para SeekBar
        binding.SeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(sb: SeekBar?, value: Int, isUser: Boolean) {
                binding.TextSeekValue.text = "Valor: $value"
            }

            override fun onStartTrackingTouch(sb: SeekBar?) {
                // Captura quando o usuário começa alterar o valor da barra
            }

            override fun onStopTrackingTouch(sb: SeekBar?) {
                // Captura quando o usuário termina de alterar o valor da barra
            }
        })

        // Código botão Next
        binding.ImageNext.setOnClickListener{
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }

    private fun setSpinnerMonths(){
        val months = listOf("Janeiro", "Fevereiro", "Novembro", "Dezembro")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, months)
        binding.spinnerMonthsAvailable.adapter = adapter
    }
}

