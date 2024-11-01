package com.example.introducaonavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.introducaonavigation.databinding.FragmentABinding
import com.example.introducaonavigation.databinding.FragmentBBinding


class BFragment : Fragment() {

    private var _binding: FragmentBBinding? = null
    private val binding get() = _binding!!
    private val args: BFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentBBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textFragmentB.text =
            "Nome do produto: ${args.product.name}\nPreço: ${args.product.price}"

        binding.textFragmentB.setOnClickListener({
            findNavController().navigate(R.id.action_BFragment_to_AFragment)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}