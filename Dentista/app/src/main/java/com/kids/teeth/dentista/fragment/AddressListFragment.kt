package com.kids.teeth.dentista.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.databinding.FragmentAddressListBinding

class AddressListFragment : Fragment() {

    private var _binding: FragmentAddressListBinding? = null
    private val binding: FragmentAddressListBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddressListBinding
            .inflate(
                inflater,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAddAddress.setOnClickListener{
            findNavController().navigate(R.id.action_AddressListFragment_to_AddressRegisterFragment)
        }
    }

}