package com.kids.teeth.dentista.fragment

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.activity.SignUpActivity
import com.kids.teeth.dentista.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding get() = _binding!!

    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Toast.makeText(this, "SingUpActivity - onCreate()", Toast.LENGTH_SHORT).show()
        _binding = FragmentSignUpBinding
            .inflate(
                inflater,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Toast.makeText(this, "SingUpActivity - onCreate()", Toast.LENGTH_SHORT).show()

        binding.btnConcludeSignUp.setOnClickListener {
            //Toast.makeText(this, "SingUpActivity - onCreate()", Toast.LENGTH_SHORT).show()
            register()
        }

    }

    override fun onStart() {
        super.onStart()
        db = FirebaseFirestore.getInstance(Firebase.app)
    }

    private fun register() {
        val fieldName = binding.etNameSignUp
        val fieldEmail = binding.etEmailSignUp
        val fieldPassword = binding.etPasswordSignUp

        val name = fieldName.text.toString()
        val email = fieldEmail.text.toString()
        val password = fieldPassword.text.toString()

        val dentist = hashMapOf(
            "name" to name,
            "email" to email,
            "password" to password
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