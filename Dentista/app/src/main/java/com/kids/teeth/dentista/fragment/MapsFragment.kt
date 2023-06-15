package com.kids.teeth.dentista.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kids.teeth.dentista.databinding.FragmentMapsBinding

class MapsFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentMapsBinding? = null
    private val binding: FragmentMapsBinding get() = _binding!!

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private lateinit var db: FirebaseFirestore

    private lateinit var auth: FirebaseAuth

    private var currentLatLng: LatLng? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.lastLocation?.let { location ->
                    currentLatLng = LatLng(location.latitude, location.longitude)
                    map.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng))
                }
            }
        }

        val mapFragment = childFragmentManager.findFragmentByTag("map_fragment_tag") as SupportMapFragment?
        if (mapFragment != null) {
            mapFragment.getMapAsync(this)
        }

        binding.btnSendLatLng.setOnClickListener {
            val uid = auth.currentUser?.uid
            val latLng = currentLatLng
            if (uid != null && latLng != null) {
                db.collection("dentists").document(uid)
                    .update("latLng", latLng)
                    .addOnSuccessListener {
                        // Atualização bem-sucedida
                    }
                    .addOnFailureListener { e ->
                        // Tratamento de falha na atualização
                    }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        if (isLocationPermissionGranted()) {
            enableMyLocation()
            startLocationUpdates()
        } else {
            requestLocationPermission()
        }
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

    private fun enableMyLocation() {
        try {
            map.isMyLocationEnabled = true
            map.uiSettings.isMyLocationButtonEnabled = true
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation()
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

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}