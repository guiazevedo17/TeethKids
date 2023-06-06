package com.kids.teeth.dentista.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.databinding.ActivitySignInBinding
import com.kids.teeth.dentista.fragment.EmergenciesListFragment


class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var navController: NavController
    private var currentTitle : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        navController = (supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment).navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.setDisplayHomeAsUpEnabled(destination.id != R.id.SignInFragment)

            updateToolbarTitle(getFragmentTitle(destination))

        }

        val action = intent.action
        if (action == "OPEN_FRAGMENT") {
            val fragment = EmergenciesListFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()

            //clear top
        }

    }

    private fun getFragmentTitle(destination: NavDestination): String {
        val title = when(destination.id) {
            R.id.SignUpFragment -> "Criar Conta"
            R.id.ResumeFragment -> "Adicionar Currículo"

            R.id.ProfileFragment -> "Perfil"
            R.id.EditProfileFragment -> "Editar Perfil"

            R.id.AddressesListFragment -> "Endereços"
            R.id.AddressRegisterFragment -> "Cadastro de Endereço"

            R.id.EmergenciesListFragment -> "Emergências"
            R.id.EmergenciesHistoricFragment -> "Histórico de Emergências"
            R.id.ReputationProfileFragment -> "Minha Reputação"
            R.id.RequestFeedbackFragment -> "Solicitar Avaliação"

            else -> "Teeth Kids - Dentista"
        }

        return title
    }

    private fun updateToolbarTitle(title: String) {
        supportActionBar?.title = title
        currentTitle = title
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}