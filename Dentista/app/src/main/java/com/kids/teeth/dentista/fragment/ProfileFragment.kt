package com.kids.teeth.dentista.fragment

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.databinding.FragmentProfileBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding get() = _binding!!

    private lateinit var db: FirebaseFirestore

    private lateinit var auth: FirebaseAuth

    private var capturedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(
            inflater, container, false
        )
        db = FirebaseFirestore.getInstance(Firebase.app)
        auth = FirebaseAuth.getInstance(Firebase.app)

        val currentUser = auth.currentUser
        if(currentUser != null) {
            val uid = currentUser.uid
            db.collection("dentists").document(uid).get().addOnSuccessListener { document ->
                val name = document.getString("name")
                val picture = document.getString("picture")
                Log.d("ProfileFragment", "URL da imagem: $picture")
                if (picture != null) {
                    Glide.with(this)
                        .load(picture)
                        .apply(RequestOptions.circleCropTransform()) // Aplica a transformação de círculo
                        .into(binding.ivProfilePicture)
                }
                binding.tvNameProfile.text = name
                Log.i("ProfileFragment", "name = $name")
            }
        }


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        db = FirebaseFirestore.getInstance(Firebase.app)
        auth = FirebaseAuth.getInstance(Firebase.app)
        returnStatus(db, auth)
    }



    private fun returnStatus(db: FirebaseFirestore, auth: FirebaseAuth) {
        val currentUser = auth.currentUser

        currentUser?.let { user ->
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val document = db.collection("dentists").document(user.uid).get().await()
                    val currentAvailability = document.getBoolean("availability")
                    if (currentAvailability != null) {
                        binding.scStatusProfile.isChecked = currentAvailability
                    }
                    setImageStatus(currentAvailability)
                } catch (e: Exception) {
                    // Trate qualquer exceção que possa ocorrer ao buscar os dados
                    e.printStackTrace()
                }
            }

        }
    }

    private fun checkAddress(db: FirebaseFirestore, auth: FirebaseAuth, callback: (Boolean) -> Unit) {

    }
    private fun setImageStatus(availability: Boolean?) {
        if (availability == true) {
            binding.ivStatusFeedback.setImageResource(R.drawable.ic_status_on)
        } else {
            binding.ivStatusFeedback.setImageResource(R.drawable.ic_status_off)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editBorder = GradientDrawable()
        editBorder.setColor(Color.TRANSPARENT)
        editBorder.setStroke(10, ContextCompat.getColor(requireContext(), R.color.tk_blue))
        editBorder.cornerRadius = 30f

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            binding.btnEditProfile.background = editBorder
        } else {
            binding.btnEditProfile.setBackgroundDrawable(editBorder)
        }

        val btnBorder = GradientDrawable()
        btnBorder.setColor(ContextCompat.getColor(requireContext(), R.color.tk_blue))
        btnBorder.setStroke(6, ContextCompat.getColor(requireContext(), R.color.tk_blue))
        btnBorder.cornerRadius = 30f

        binding.btnAddressProfile.background = btnBorder
        binding.btnEmergenciesProfile.background = btnBorder
        binding.btnReputationProfile.background = btnBorder

        binding.btnLogoutProfile.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.action_ProfileFragment_to_SignInFragment)
        }

        binding.scStatusProfile.setOnClickListener {
            val isChecked = binding.scStatusProfile.isChecked

            setImageStatus(isChecked)

            auth.currentUser?.let { user ->
                db.collection("dentists").document(user.uid).get()
                    .addOnSuccessListener { document ->
                        val currentAvailability = document.getBoolean("availability")
                        if (currentAvailability != null) {
                            val newAvailability = !currentAvailability
                            db.collection("dentists").document(user.uid)
                                .update("availability", newAvailability)
                        }
                    }
            }
        }

        binding.btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_ProfileFragment_to_EditProfileFragment)
        }

        binding.btnAddressProfile.setOnClickListener { // Endereços

            findNavController().navigate(R.id.action_ProfileFragment_to_AddressesListProfileFragment)
        }

        binding.btnEmergenciesProfile.setOnClickListener { // Emergências
            findNavController().navigate(R.id.action_ProfileFragment_to_EmergenciesListFragment)
        }

        binding.btnReputationProfile.setOnClickListener { // Minha Reputação
            findNavController().navigate(R.id.action_ProfileFragment_to_ReputationFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()

    }

}