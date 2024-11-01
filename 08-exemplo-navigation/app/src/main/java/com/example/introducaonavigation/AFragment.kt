package com.example.introducaonavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.introducaonavigation.databinding.FragmentABinding


class AFragment : Fragment() {

    private var _binding: FragmentABinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentABinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textFragmentA.setOnClickListener({

            val prod = Product("Monitor TV", 1000f)
            val action = AFragmentDirections.actionAFragmentToBFragment(prod)
            findNavController().navigate(action)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}