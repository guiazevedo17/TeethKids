package com.kids.teeth.dentista.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kids.teeth.dentista.databinding.FragmentResumeBinding


class ResumeFragment : Fragment() {

    private var _binding: FragmentResumeBinding? = null
    private val binding: FragmentResumeBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResumeBinding
            .inflate(
                inflater,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnConcludeResume.setOnClickListener {
            val resume = binding.etResumeRegister.text.toString()

            val direction = ResumeFragmentDirections.actionResumeFragmentToSignUpFragment()

            direction.resumeRegistered = resume

            findNavController().navigate(direction)
        }
    }


}