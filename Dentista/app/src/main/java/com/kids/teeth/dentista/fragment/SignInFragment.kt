package com.kids.teeth.dentista.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.databinding.FragmentSignInBinding
import com.kids.teeth.dentista.databinding.FragmentSignUpBinding

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding: FragmentSignInBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding
            .inflate(
                inflater,
                container,
                false
            )
        return binding.root
    }


}