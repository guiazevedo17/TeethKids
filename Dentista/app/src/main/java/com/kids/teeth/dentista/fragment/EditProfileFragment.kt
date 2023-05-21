package com.kids.teeth.dentista.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding: FragmentEditProfileBinding get() = _binding!!

    private lateinit var db: FirebaseFirestore

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding
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

        recoverData(db)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibtnBackEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_EditProfileFragment_to_ProfileFragment)
        }
    }

    private fun recoverData(db : FirebaseFirestore){

        val currentUser = auth.currentUser
        if (currentUser != null){
            val uid = currentUser.uid

            db.collection("dentists")
                .document(uid)
                .get()
                .addOnSuccessListener { document ->
                    val name = document.getString("name")
                    val phone = document.getString("phone")
                    val email = document.getString("email")
                    val password = document.getString("password")
                    val resume = document.getString("resume")

                    binding.etNameEditProfile.setText(name)
                    binding.etPhoneEditProfile.setText(phone)
                    binding.etEmailEditProfile.setText(email)
                    binding.etPasswordEditProfile.setText(password)
                    binding.etResumeEditProfile.setText(resume)
                }

        }

    }


}