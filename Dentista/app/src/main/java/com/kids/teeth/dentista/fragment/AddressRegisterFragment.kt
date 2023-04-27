package com.kids.teeth.dentista.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kids.teeth.dentista.databinding.FragmentAddressRegisterBinding

class AddressRegisterFragment : Fragment() {

    private var _binding: FragmentAddressRegisterBinding? = null
    private val binding: FragmentAddressRegisterBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddressRegisterBinding
            .inflate(
                inflater,
                container,
                false
            )
        return binding.root
    }
}