package com.kids.teeth.dentista.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.kids.teeth.dentista.dao.AddressesDao
import com.kids.teeth.dentista.databinding.FragmentAddressRegisterBinding
import com.kids.teeth.dentista.model.Address

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnConcludeAddressRegister.setOnClickListener {

            val newAddress = createAddress()

            val direction = AddressRegisterFragmentDirections.actionAddressRegisterFragmentToAddressListFragment()

            AddressesDao.add(newAddress)

            findNavController().navigate(direction)

            Toast.makeText(requireContext(),"AddressRegisterFragment onViewCreated() - ${AddressesDao.searchAll()}", Toast.LENGTH_LONG).show()

            cleanFields()

        }
    }

    private fun createAddress(): Address {

        return Address(
            name = binding.etName.text.toString(),
            code = binding.etPostalCode.text.toString(),
            street = binding.etStreet.text.toString(),
            number = binding.etNumber.text.toString(),
            complement = binding.etComplement.text.toString(),
            neighborhood = binding.etNeighborhood.text.toString(),
            city = binding.etCity.text.toString(),
            state = binding.etState.text.toString(),
        )
    }

    private fun cleanFields(){
        binding.etName.setText("")
        binding.etPostalCode.setText("")
        binding.etStreet.setText("")
        binding.etNumber.setText("")
        binding.etComplement.setText("")
        binding.etNeighborhood.setText("")
        binding.etCity.setText("")
        binding.etState.setText("")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}