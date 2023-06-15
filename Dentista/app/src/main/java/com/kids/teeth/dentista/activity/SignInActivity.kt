package com.kids.teeth.dentista.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Define o modo de tela cheia para dispositivos com Android 11 ou superior
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let { controller ->
                controller.hide(WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            // Define o modo de tela cheia para dispositivos com versões anteriores ao Android 11
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_IMMERSIVE)
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        Log.w("SingInActivity", "navController - $navController")

        val action = intent.action
        if (action == "OPEN_FRAGMENT_EMERCIES_LIST") {
            Log.w("SingInActivity", "abrindo EmergenciesListFragment")
            navController.navigate(R.id.EmergenciesListFragment)
        }

        if (action == "OPEN_FRAGMENT_MAPS") {
            Log.w("SingInActivity", "abrindo MpasFragment")
            navController.navigate(R.id.MapFragment)
        }

        if (action == "OPEN_FRAGMENT_EMERGENCY_IN_PROGRESS") {
            Log.w("SingInActivity", "abrindo EmergencyInProgress")
            navController.navigate(R.id.EmergencyInProgressFragment)
        }

    }

}