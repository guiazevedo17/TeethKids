package com.kids.teeth.dentista.fragment

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.databinding.FragmentReputationProfileBinding

class ReputationProfileFragment : Fragment() {

    private var _binding: FragmentReputationProfileBinding? = null
    private val binding: FragmentReputationProfileBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReputationProfileBinding
            .inflate(
                inflater,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spannableString = SpannableString(binding.tvReputationCasesQntd.text)
        spannableString.setSpan(UnderlineSpan(), 0, binding.tvReputationCasesQntd.text.length, 0)

        binding.tvReputationCasesQntd.text = spannableString

        binding.btnBackReputation.setOnClickListener {
            findNavController().navigate(R.id.action_ReputationProfileFragment_to_ProfileFragment)
        }

        binding.btnReputationFeedbacks.setOnClickListener {
            findNavController().navigate(R.id.action_ReputationProfileFragment_to_FeedbacksListFragment)
        }
    }


}