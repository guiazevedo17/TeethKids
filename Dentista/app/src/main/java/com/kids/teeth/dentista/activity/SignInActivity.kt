package com.kids.teeth.dentista.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}