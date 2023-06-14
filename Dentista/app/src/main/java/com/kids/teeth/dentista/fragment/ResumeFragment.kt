package com.kids.teeth.dentista.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kids.teeth.dentista.R
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

        val args = this.arguments

        binding.btnBackResumeSignUp.setOnClickListener {
            val bundle = passData(args)

            Log.w("ResumeFragment", "BACK: name = ${bundle.getString("name")} | phone = ${bundle.getString("phone")} | email = ${bundle.getString("email")} | password = ${bundle.getString("password")}")


            findNavController().navigate(R.id.action_ResumeFragment_to_SignUpFragment, bundle)
        }

        binding.btnConcludeResume.setOnClickListener {
            val bundle = passData(args)

            Log.w("ResumeFragment", "CONCLUDE: name = ${bundle.getString("name")} | phone = ${bundle.getString("phone")} | email = ${bundle.getString("email")} | password = ${bundle.getString("password")}")

            findNavController().navigate(R.id.action_ResumeFragment_to_SignUpFragment,bundle)
        }
    }

    private fun passData(args: Bundle?) : Bundle{
        val bundle = Bundle()
        bundle.putString("name", args?.getString("name"))
        bundle.putString("phone", args?.getString("phone"))
        bundle.putString("email", args?.getString("email"))
        bundle.putString("password", args?.getString("password"))
        bundle.putString("confPassword", args?.getString("confPassword"))
        bundle.putString("resume", binding.etResume.text.toString())

        return bundle
    }


}