package com.example.introducaonavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.introducaonavigation.databinding.FragmentInitBinding


class InitFragment : Fragment() {

    private var _binding: FragmentInitBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentInitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAbout.setOnClickListener({
            findNavController().navigate(R.id.actionInitToAbout)
        })

        binding.buttonContact.setOnClickListener({
            findNavController().navigate(R.id.actionInitToContact)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}