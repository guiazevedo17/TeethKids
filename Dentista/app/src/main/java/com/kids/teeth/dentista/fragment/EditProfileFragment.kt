package com.kids.teeth.dentista.fragment

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.dao.AddressesDao
import com.kids.teeth.dentista.databinding.FragmentEditProfileBinding
import com.kids.teeth.dentista.model.Address

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
        auth = FirebaseAuth.getInstance(Firebase.app)

        recoverData(db)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBorder = GradientDrawable()
        btnBorder.setColor(ContextCompat.getColor(requireContext(), R.color.tk_blue))
        btnBorder.setStroke(6, ContextCompat.getColor(requireContext(),R.color.tk_blue))
        btnBorder.cornerRadius = 30f

        binding.btnConcludeEditProfile.background = btnBorder

        binding.btnCancelEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_EditProfileFragment_to_ProfileFragment)
        }

        binding.btnConcludeEditProfile.setOnClickListener {
            val Name = binding.etNameEditProfile.text.toString()
            val Phone = binding.etPhoneEditProfile.text.toString()
            val Email = binding.etEmailEditProfile.text.toString()
            val Password = binding.etPasswordEditProfile.text.toString()
            val PasswordConfirm = binding.etConfPasswordEditProfile.text.toString()
            val Resume = binding.etResumeEditProfile.text.toString()

            if(confirmPassword(Password,PasswordConfirm)){

                if(fieldNotNull(Name, Phone, Email, Password, PasswordConfirm, Resume)){
                    updateData(Name,Phone,Email,Password,Resume)
                    findNavController().navigate(R.id.action_EditProfileFragment_to_ProfileFragment)
                    val snackbar = Snackbar.make(view, "Sucesso ao cadastrar usuario!", Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.BLUE)
                    snackbar.show()
                }
                else{
                    val snackbar = Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }
            }
            else{
                val snackbar = Snackbar.make(view, "As senhas nÃ£o correspondem!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }
        }
    }

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

    private fun updateData(Name: String, Phone: String, Email: String, Password: String, Resume: String) {

        val updateData = hashMapOf(
            "name" to Name,
            "phone" to Phone,
            "email" to Email,
            "password" to Password,
            "resume" to Resume,
        )

        auth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener { update ->
            auth.currentUser?.let { user ->
                db.collection("dentists").document(user.uid).get()
                    .addOnSuccessListener { document ->
                        db.collection("dentists").document(user.uid)
                            .update("name", Name,
                                "phone", Phone,
                                "email", Email,
                                "password", Password,
                                "resume", Resume
                            )
                    }
            }
        }

    }

    private fun clearFields(){
        binding.etNameEditProfile.setText("")
        binding.etEmailEditProfile.setText("")
        binding.etPhoneEditProfile.setText("")
        binding.etPasswordEditProfile.setText("")
        binding.etConfPasswordEditProfile.setText("")
        binding.etResumeEditProfile.setText("")
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

                    println(name)

                    binding.etNameEditProfile.setText(name)
                    binding.etPhoneEditProfile.setText(phone)
                    binding.etEmailEditProfile.setText(email)
                    binding.etPasswordEditProfile.setText(password)
                    binding.etResumeEditProfile.setText(resume)
                }

        }

        println("recoverData()")

    }


}