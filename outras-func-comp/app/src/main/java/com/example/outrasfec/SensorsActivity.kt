package com.example.outrasfec

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.outrasfec.databinding.ActivitySensorsBinding

class SensorsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySensorsBinding

    private lateinit var sensorManager: SensorManager
    private lateinit var sensorListener: SensorEventListener
    private lateinit var acc: Sensor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySensorsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        acc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        // Apenas caso queria saber todos os sensores disponíveis
        val allSensors = sensorManager.getSensorList(Sensor.TYPE_ALL)

        if (acc != null) {
            sensorListener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                        val x = event.values[0]
                        val y = event.values[1]
                        val z = event.values[2]
                        binding.textAxisX.text = "Eixo X: ${x.toString()}"
                        binding.textAxisY.text = "Eixo Y: ${y.toString()}"
                        binding.textAxisZ.text = "Eixo Z: ${z.toString()}"
                    }
                }
                override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

                }
            }
        } else {
            binding.textAxisX.text = "Eixo X: 0"
            binding.textAxisY.text = "Eixo Y: 0"
            binding.textAxisZ.text = "Eixo Z: 0"
        }

    }

    override fun onStart() {
        super.onStart()
        sensorManager.registerListener(sensorListener, acc, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorListener)
    }
}