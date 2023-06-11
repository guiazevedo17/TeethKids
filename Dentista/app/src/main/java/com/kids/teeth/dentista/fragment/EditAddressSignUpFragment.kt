package com.kids.teeth.dentista.fragment

import android.os.Bundle
import android.text.Editable
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
import com.kids.teeth.dentista.databinding.FragmentEditAddressSignUpBinding
import com.kids.teeth.dentista.model.Address

class EditAddressSignUpFragment : Fragment() {

    private var _binding: FragmentEditAddressSignUpBinding? = null
    private val binding: FragmentEditAddressSignUpBinding get() = _binding!!

    private lateinit var functions: FirebaseFunctions

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditAddressSignUpBinding
            .inflate(
                inflater,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = this.arguments
        val addressName = args?.getString("name")

        binding.etName.text = addressName as Editable?

        binding.btnCancelEditAddressSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_AddressRegisterProfileFragment_to_AddressesListProfileFragment)
        }

        binding.btnConcludeEditAddressSignUp.setOnClickListener {

            val newAddress = createAddress() // Passar para Update


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

    private fun storeFcmToken(Name: String, Phone: String, Email: String, Password: String, Resume: String,Uid: String){
        Firebase.messaging.token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            // guardar esse token.
            var fcmToken = task.result
            registerAddress(Name,Phone,Email,Password,Resume,AddressesDao.searchAll(), Uid,false,fcmToken)

        })
    }

    private fun registerAddress(Name: String, Phone: String, Email: String, Password: String, Resume: String, Addresses: List<Address>, Uid: String, Availability: Boolean,Fcmtoken: String) {

        functions = Firebase.functions("southamerica-east1")

        val address = hashMapOf(
            "name" to Name,
            "phone" to Phone,
            "email" to Email,
            "password" to Password,
            "resume" to Resume,
            "addresses" to Addresses,
            "availability" to Availability,
            "fcmToken" to Fcmtoken
        )

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