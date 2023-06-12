package com.kids.teeth.dentista.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.databinding.ActivitySignInBinding
import com.kids.teeth.dentista.fragment.EmergenciesListFragment


class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        Log.w("SingInActivity", "navController - $navController")

        val action = intent.action
        if (action == "OPEN_FRAGMENT") {
            Log.w("SingInActivity", "abrindo EmergenciesListFragment")
            navController.navigate(R.id.EmergenciesListFragment)
        }

    }

}