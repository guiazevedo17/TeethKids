package com.kids.teeth.dentista.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kids.teeth.dentista.databinding.FragmentEmergencyInProgressBinding

class EmergencyInProgressFragment : Fragment() {

    private var _binding: FragmentEmergencyInProgressBinding? = null
    private val binding: FragmentEmergencyInProgressBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmergencyInProgressBinding
            .inflate(
                inflater,
                container,
                false
            )
        return binding.root
    }
}