package com.kids.teeth.dentista.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.ktx.messaging
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.databinding.FragmentEmergencyInProgressBinding
import com.kids.teeth.dentista.messaging.DefaultMessageService
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

class EmergencyInProgressFragment : Fragment() {

    private var _binding: FragmentEmergencyInProgressBinding? = null
    private val binding: FragmentEmergencyInProgressBinding get() = _binding!!

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var functions: FirebaseFunctions

    private lateinit var fcmToken:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmergencyInProgressBinding
            .inflate(
                inflater,
                container,
                false
            )
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        auth = FirebaseAuth.getInstance(Firebase.app)
        functions = Firebase.functions("southamerica-east1")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getEmergencyInfo()

        binding.btnConcludeEmergency.setOnClickListener {
            val msgType = "rating"
            val dentistId = auth.currentUser?.uid


            val ex = hashMapOf(
                "fcmToken" to fcmToken,
                "messageType" to msgType,
                "id" to dentistId
            )

            Log.w("EmergencyInProgress", "fcmToken - $fcmToken")

            functions.getHttpsCallable("sendRTS")
                .call(ex)
                .addOnSuccessListener { result ->
                    val resposta: String? = result.data.toString()
                    Log.d("setUserResult", "Result : ${resposta}")
                }

            findNavController().navigate(R.id.action_EmergencyInProgressFragment_to_ProfileFragment)
        }
    }

    private fun getEmergencyInfo() {
        db = FirebaseFirestore.getInstance(Firebase.app)

        db.collection("emergencies").document(DefaultMessageService.docID.toString()).get()
            .addOnSuccessListener { document ->
                val requesterName = document.getString("name")
                val requesterPhone = document.getString("phone")
                val imageRefs = document.get("images") as ArrayList<String>
                fcmToken = document.getString("fcmToken") as String

                val timestamp = document.get("data") as? com.google.firebase.Timestamp
                val date = timestamp?.toDate()
                val sdf = SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault())
                val formattedDate = sdf.format(date)

                if (requesterName != null && date != null) {
                    binding.tvRequesterName.text = capitalizeWords(requesterName)
                    binding.btnCallPhone.text = requesterPhone
                    binding.tvEmergencyDate.text = formattedDate
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

            }
    }

    private fun capitalizeWords(requesterName: String): String {
        val words = requesterName.split(" ")
        val capitalizedWords = words.map { it.capitalize(Locale.ROOT) }
        return capitalizedWords.joinToString(" ")
    }


}