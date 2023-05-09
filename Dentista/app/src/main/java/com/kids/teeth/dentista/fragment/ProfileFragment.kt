package com.kids.teeth.dentista.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.databinding.FragmentProfileBinding
import com.kids.teeth.dentista.databinding.FragmentSignInBinding
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
    }

}