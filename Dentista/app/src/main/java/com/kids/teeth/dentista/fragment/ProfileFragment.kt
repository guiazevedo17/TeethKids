package com.kids.teeth.dentista.fragment

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val binding : FragmentProfileBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding
            .inflate(
                inflater,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editBorder = GradientDrawable()
        editBorder.setColor(Color.TRANSPARENT)
        editBorder.setStroke(10,ContextCompat.getColor(requireContext(),R.color.tk_blue))
        editBorder.cornerRadius = 30f

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            binding.btnEditProfile.background = editBorder
        } else {
            binding.btnEditProfile.setBackgroundDrawable(editBorder)
        }

        val btnBorder = GradientDrawable()
        btnBorder.setColor(ContextCompat.getColor(requireContext(), R.color.tk_blue))
        btnBorder.setStroke(6,ContextCompat.getColor(requireContext(),R.color.tk_blue))
        btnBorder.cornerRadius = 30f

        binding.btnAddressProfile.background = btnBorder
        binding.btnEmergenciesProfile.background = btnBorder
        binding.btnReputationProfile.background = btnBorder
        binding.btnRequestFeedbackProfile.background = btnBorder

        binding.scStatusProfile.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                binding.ivStatusFeedback.setImageResource(R.drawable.ic_status_on)
            } else {
                binding.ivStatusFeedback.setImageResource(R.drawable.ic_status_off)
            }
        }

        binding.ibtnLogout.setOnClickListener {
            findNavController().navigate(R.id.action_ProfileFragment_to_SignInFragment)
        }

        binding.btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_ProfileFragment_to_EditProfileFragment)
        }

        binding.btnAddressProfile.setOnClickListener { // Endereços

            val direction = ProfileFragmentDirections.actionProfileFragmentToAddressesListFragment()

            direction.loggedIn = true

            findNavController().navigate(direction)
        }

//        binding.btnEmergenciesProfile.setOnClickListener { // Emergências
//            findNavController().navigate(R.id.action_ProfileFragment_to_EmergenciesListFragment)
//        }
//
//        binding.btnReputationProfile.setOnClickListener { // Minha Reputação
//            findNavController().navigate(R.id.action_ProfileFragment_to_ReputationFragment)
//        }
//
//        binding.btnRequestFeedbackProfile.setOnClickListener { // Solicitar Avaliação
//            findNavController().navigate(R.id.action_ProfileFragment_to_RequestFeedbackFragment)
//        }
    }

}