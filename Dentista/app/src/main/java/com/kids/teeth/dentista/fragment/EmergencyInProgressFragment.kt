package com.kids.teeth.dentista.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
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

    private lateinit var fcmToken: String

    private var currentLatLng: LatLng? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private val LOCATION_PERMISSION_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmergencyInProgressBinding.inflate(inflater, container, false)
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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000

        binding.btnSendLocation.setOnClickListener {

            val uid = auth.currentUser?.uid
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    locationResult?.lastLocation?.let { location ->
                        currentLatLng = LatLng(location.latitude, location.longitude)
                        currentLatLng = LatLng(location.latitude, location.longitude)
                        val latitude = location.latitude
                        val longitude = location.longitude
                        val latitudeStr = latitude.toString()
                        val longitudeStr = longitude.toString()

                        Firebase.messaging.token.addOnCompleteListener(OnCompleteListener { task ->
                            if (!task.isSuccessful) {
                                return@OnCompleteListener
                            }
                            // guardar esse token.
                            fcmToken = task.result

                            val hashMap = hashMapOf(
                                "latitude" to latitudeStr,
                                "longitude" to longitudeStr,
                                "fcmToken" to fcmToken
                            )

                            functions.getHttpsCallable("sendLocation")
                                .call(hashMap)
                                .addOnSuccessListener { result ->
                                    val resposta: String = result.data.toString()
                                    //Log.d("setUserResult", "Result : ${resposta}")
                                    val snackbar = Snackbar.make(view, "Localização Enviada com Sucesso!", Snackbar.LENGTH_SHORT)
                                    snackbar.setBackgroundTint(Color.BLUE)
                                    snackbar.show()
                                }.addOnFailureListener { exception ->
                                    val snackbar = Snackbar.make(view, "Não foi Possivel Enviar sua Localização!", Snackbar.LENGTH_SHORT)
                                    snackbar.setBackgroundTint(Color.RED)
                                    snackbar.show()
                                }
                        })
                    }
                }
            }

            startLocationUpdates()
        }

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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        requestPermissions(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()
            }
        }
    }

    private fun startLocationUpdates() {
        if (isLocationPermissionGranted()) {
            try {
                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
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
