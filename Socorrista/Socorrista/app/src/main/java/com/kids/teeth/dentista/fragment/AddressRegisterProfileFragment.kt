package com.kids.teeth.dentista.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.dao.AddressesDao
import com.kids.teeth.dentista.databinding.FragmentAddressRegisterProfileBinding
import com.kids.teeth.dentista.model.Address

class AddressRegisterProfileFragment : Fragment() {

    private var _binding: FragmentAddressRegisterProfileBinding? = null
    private val binding: FragmentAddressRegisterProfileBinding get() = _binding!!

    private lateinit var functions: FirebaseFunctions

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddressRegisterProfileBinding
            .inflate(
                inflater,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBackAddressRegisterProfile.setOnClickListener {
            clearFields()
            findNavController().navigate(R.id.action_AddressRegisterProfileFragment_to_AddressesListProfileFragment)
        }

        binding.btnConcludeAddressRegister.setOnClickListener {
            val newAddress = createAddress()
            registerAddress(newAddress)
            clearFields()

            findNavController().navigate(R.id.action_AddressRegisterProfileFragment_to_AddressesListProfileFragment)
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

    private fun registerAddress(address: Address) {

        functions = Firebase.functions("southamerica-east1")

        functions.getHttpsCallable("setAddress")
            .call(address)
            .addOnSuccessListener { result ->
                val resposta :String? = result.data.toString()
            }

    }

    private fun clearFields(){
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