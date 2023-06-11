package com.kids.teeth.dentista.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.databinding.FragmentEmergencyDetailBinding
import java.util.Locale

class EmergencyDetailFragment : Fragment() {

    private var _binding: FragmentEmergencyDetailBinding? = null
    private val binding: FragmentEmergencyDetailBinding get() = _binding!!

    private lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmergencyDetailBinding.inflate(
            inflater, container, false
        )

        val args = this.arguments
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
        
        return binding.root
    }


    private fun capitalizeWords(requesterName: String): String {
        val words = requesterName.split(" ")
        val capitalizedWords = words.map { it.capitalize(Locale.ROOT) }
        return capitalizedWords.joinToString(" ")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBackEmergencyDetail.setOnClickListener {
            findNavController().navigate(R.id.action_EmergencyDetailFragment_to_EmergenciesListFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}