package com.example.introducaofragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.introducaofragment.databinding.FragmentBBinding

class BFragment : Fragment(R.layout.fragment_b) {

    private var _binding: FragmentBBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prod = requireArguments().getSerializable("PROD") as Product

        Log.i("FRAG_A", "Product ID: ${prod.id}")
        Log.i("FRAG_A", "Product Name: ${prod.name}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentBBinding.inflate(inflater, container, false)
        binding.textFragmentB.text = "Fragmento B"
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}