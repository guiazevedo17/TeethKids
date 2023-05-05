package com.kids.teeth.dentista.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.databinding.FragmentSignInBinding
import com.kids.teeth.dentista.databinding.FragmentSignUpBinding

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding: FragmentSignInBinding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding
            .inflate(
                inflater,
                container,
                false
            )
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCreateAccount.setOnClickListener {
            findNavController().navigate(R.id.action_SignInFragment_to_SignUpFragment)
        }

        binding.btnLogin.setOnClickListener{

            val Email = binding.etEmailSignIn.text.toString()
            val Password = binding.etPasswordSignIn.text.toString()

            auth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener { login ->
                if (login.isSuccessful) {
                    findNavController().navigate(R.id.action_SignInFragment_to_ProfileFragment)
                }
                else {
                    val snackbar = Snackbar.make(view, "Email ou senha incorretos!", Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }
            }
        }
    }
}