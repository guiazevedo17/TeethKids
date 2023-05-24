package com.kids.teeth.dentista.fragment

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.databinding.FragmentEmergenciesHistoricBinding

class EmergenciesHistoricFragment : Fragment() {

    private var _binding : FragmentEmergenciesHistoricBinding? = null
    private val binding : FragmentEmergenciesHistoricBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmergenciesHistoricBinding
            .inflate(
                inflater,
                container,
                false
            )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}