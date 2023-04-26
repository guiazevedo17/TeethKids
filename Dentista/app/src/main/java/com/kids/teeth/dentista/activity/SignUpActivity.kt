package com.kids.teeth.dentista.activity

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity(R.layout.fragment_sign_up) {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}