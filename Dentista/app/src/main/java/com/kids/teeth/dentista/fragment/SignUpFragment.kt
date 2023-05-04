package com.kids.teeth.dentista.fragment

import android.content.ContentValues
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.dao.AddressesDao
import com.kids.teeth.dentista.databinding.FragmentSignUpBinding
import com.kids.teeth.dentista.model.Address


class SignUpFragment : Fragment(){

    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding get() = _binding!!

    private lateinit var db: FirebaseFirestore

    private lateinit var auth: FirebaseAuth

    private val arguments by navArgs<SignUpFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding
            .inflate(
                inflater,
                container,
                false
            )
        return binding.root
    }
    override fun onStart() {
        super.onStart()
        db = FirebaseFirestore.getInstance(Firebase.app)
        auth = FirebaseAuth.getInstance(Firebase.app)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding.btnAddressSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_SignUpFragment_to_AddressListFragment)
        }

        binding.btnResumeSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_SignUpFragment_to_ResumeFragment)
        }

        binding.btnConcludeSignUp.setOnClickListener {
            val Name = binding.etNameSignUp.text.toString()
            val Phone = binding.etPhoneSignUp.text.toString()
            val Email = binding.etEmailSignUp.text.toString()
            val Password = binding.etPasswordSignUp.text.toString()
            val PasswordConfirm = binding.etConfPasswordSignUp.text.toString()
            val Resume = arguments.resumeRegistered

            if(confirmPassword(Password,PasswordConfirm)){

                if(fieldNotNull(Name, Phone, Email, Password, PasswordConfirm, Resume)){

                    auth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener { register ->
                        if (register.isSuccessful) {
                            val snackbar = Snackbar.make(view, "Sucesso ao cadastrar usuario!", Snackbar.LENGTH_SHORT)
                            snackbar.setBackgroundTint(Color.BLUE)
                            snackbar.show()
                            registerAccount(Name,Phone,Email,Password,Resume,AddressesDao.searchAll())
                            clearFields()
                        }
                    }
                }
                else{
                    val snackbar = Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }
            }
            else{
                val snackbar = Snackbar.make(view, "As senhas não correspondem!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }
            //register()
        }

    }



    // Limpa os campos
    private fun clearFields(){
        binding.etNameSignUp.setText("")
        binding.etEmailSignUp.setText("")
        binding.etPhoneSignUp.setText("")
        binding.etPasswordSignUp.setText("")
        binding.etConfPasswordSignUp.setText("")
    }

    // Verifica compatibilidade das senhas
    private fun confirmPassword(Password: String, PasswordConfirm: String): Boolean {

        if(Password == PasswordConfirm) {
            return true
        }
        return false
    }

    // Verifica se existe algum campo nulo
    private fun fieldNotNull(Name: String, Phone: String, Email: String, Password: String, PasswordConfirm: String, Resume: String): Boolean {

        if(Name.isEmpty() || Phone.isEmpty() || Email.isEmpty() || Password.isEmpty() || PasswordConfirm.isEmpty() || Resume.isEmpty()){
            return false
        }
        return true
    }

    private fun registerAccount(Name: String, Phone: String, Email: String, Password: String, Resume: String, Addresses: List<Address>) {

        val dentist = hashMapOf(
            "name" to Name,
            "phone" to Phone,
            "email" to Email,
            "password" to Password,
            "resume" to Resume,
            "addresses" to Addresses
        )

        db.collection("dentists")
            .add(dentist)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }

    }



}