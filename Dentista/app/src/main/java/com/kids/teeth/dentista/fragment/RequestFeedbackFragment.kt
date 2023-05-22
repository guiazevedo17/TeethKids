package com.kids.teeth.dentista.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.databinding.FragmentRequestFeedbackBinding

class RequestFeedbackFragment : Fragment() {

    private var _binding: FragmentRequestFeedbackBinding? = null
    private val binding: FragmentRequestFeedbackBinding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRequestFeedbackBinding
            .inflate(
                inflater,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibtnBackRequestFeedbackProfile.setOnClickListener {
            findNavController().navigate(R.id.action_RequestFeedbackFragment_to_ProfileFragment)
        }
    }

}