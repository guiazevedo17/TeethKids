package com.kids.teeth.dentista.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.storage.FirebaseStorage
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.databinding.FragmentEmergencyDetailBinding
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.Locale

class EmergencyDetailFragment : Fragment() {

    private var _binding: FragmentEmergencyDetailBinding? = null
    private val binding: FragmentEmergencyDetailBinding get() = _binding!!

    private lateinit var storage: FirebaseStorage
    private lateinit var auth: FirebaseAuth
    private lateinit var functions: FirebaseFunctions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmergencyDetailBinding.inflate(
            inflater, container, false
        )
        auth = FirebaseAuth.getInstance(Firebase.app)

        val currentUser = auth.currentUser

        val args = this.arguments
        val emergencyId = args?.getString("emergencyId")
        val requesterName = args?.getString("name")
        val date = args?.getString("date")

        val imageRefs = args?.getStringArrayList("images")
        Log.i("EmergencyDetail", "imageRefs - Array: $imageRefs")

        if (requesterName != null && date != null) {
            binding.tvRequesterName.text = capitalizeWords(requesterName)
            binding.tvEmergencyDate.text = date
        } else {
            Log.w("inputData", "Emergency name is null!")
        }

        if (imageRefs != null) {
            if (imageRefs.size >= 1) {
                Glide.with(this).load(imageRefs[0]).into(binding.ivFirstPicture)
            }

            if (imageRefs.size >= 2) {
                Glide.with(this).load(imageRefs[1]).into(binding.ivSecondPicture)
            }

            if (imageRefs.size >= 3) {
                Glide.with(this).load(imageRefs[2]).into(binding.ivThirdPicture)
            }
        }

        binding.btnBackEmergencyDetail.setOnClickListener {
            findNavController().navigate(R.id.action_EmergencyDetailFragment_to_EmergenciesListFragment)
        }

        if (currentUser != null) {
            binding.btnAcceptEmergency.setOnClickListener {

                registerActionedEmergency(emergencyId as String, currentUser.uid, "accepted")

                findNavController().navigate(R.id.action_EmergencyDetailFragment_to_EmergenciesListFragment)
            }

            binding.btnDeclineEmergency.setOnClickListener {

                registerActionedEmergency(emergencyId as String, currentUser.uid, "declined")

                findNavController().navigate(R.id.action_EmergencyDetailFragment_to_EmergenciesListFragment)
            }
        }

        return binding.root
    }

    private fun registerActionedEmergency(Emergency: String, Dentist: String, action: String) {
        functions = Firebase.functions("southamerica-east1")

        val emergency = hashMapOf(
            "emergencyId" to Emergency,
            "dentistId" to Dentist,
            "action" to action
        )

        functions.getHttpsCallable("setActions")
            .call(emergency)
            .addOnSuccessListener { result ->
                val resposta = result.data.toString()
                Log.d("registerActionedEmergency", "Result : ${resposta}")
            }

        sendMessage()
    }

    private fun sendMessage(){
        functions = Firebase.functions("southamerica-east1")

        val args = this.arguments
        val fcmToken = args?.getString("fcmToken")
        val dentistId = auth.currentUser?.uid

        Log.d("EmergencyDetailFragment", "sendMessage - fcmToken = $fcmToken | dentistId = $dentistId")

        val emergency = hashMapOf(
            "fcmToken" to fcmToken,
            "dentistId" to dentistId
        )

        functions.getHttpsCallable("sendMessage")
            .call(emergency)
            .addOnSuccessListener { result ->
                val resposta = result.data.toString()
                Log.d("sendMessage", "Result : ${resposta}")
            }
    }


    private fun capitalizeWords(requesterName: String): String {
        val words = requesterName.split(" ")
        val capitalizedWords = words.map { it.capitalize(Locale.ROOT) }
        return capitalizedWords.joinToString(" ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}