package com.example.introducaofragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.replace
import androidx.fragment.app.commit
import com.example.introducaofragment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonFragA.setOnClickListener({
            setFragmentA()
        })

        binding.buttonFragB.setOnClickListener({
            setFragmentB()
        })
    }

    fun setFragmentA() {

        val bundle = bundleOf(
            "PROD_ID" to 202523,
            "PROD_NAME" to "Monitor TV"
        )

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<AFragment>(R.id.fragment_container_view, args=bundle)
        }
    }

    fun setFragmentB() {

        val bundle = bundleOf(
            "PROD" to Product(202523, "Monitor TV")
        )

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<BFragment>(R.id.fragment_container_view, args=bundle)
        }
    }
}