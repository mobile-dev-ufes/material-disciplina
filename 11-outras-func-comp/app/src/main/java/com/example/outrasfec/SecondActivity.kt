package com.example.outrasfec

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import com.example.outrasfec.databinding.ActivitySecondBinding
import java.util.*

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        // Código para o DatePicker
        binding.buttonDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val listener = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(dp: DatePicker?, year: Int, month: Int, day: Int) {
                    binding.textDate.text = "Data: $day/${month+1}/$year"
                }
            }
            DatePickerDialog(this, listener, cal.get(Calendar.YEAR),
                             cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        // Código para o TimePicker
        binding.buttonTime.setOnClickListener {
            val listener = object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(tp: TimePicker?, hour: Int, min: Int) {
                    binding.textTime.text = "Horário: $hour:$min"
                }
            }
            TimePickerDialog(this, listener, 12, 0, true).show()
        }

        // Código para o MaskEditText
        val isDone = binding.MaskCPF.isDone
        val masked = binding.MaskCPF.masked
        val raw = binding.MaskCPF.unMasked

        // Código para enviar para próxima tela
        binding.ImageNext.setOnClickListener {
            startActivity(Intent(this, SensorsActivity::class.java))
        }

    }
}