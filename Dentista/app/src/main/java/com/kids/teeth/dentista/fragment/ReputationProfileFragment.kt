package com.kids.teeth.dentista.fragment

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.databinding.FragmentReputationProfileBinding

class ReputationProfileFragment : Fragment() {

    private var _binding: FragmentReputationProfileBinding? = null
    private val binding: FragmentReputationProfileBinding get() = _binding!!
    private lateinit var db: FirebaseFirestore

    private lateinit var auth: FirebaseAuth
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
        db = FirebaseFirestore.getInstance(Firebase.app)
        auth = FirebaseAuth.getInstance(Firebase.app)

        val currentUser = auth.currentUser
        if(currentUser != null) {
            val uid = currentUser.uid
            db.collection("dentists").document(uid).get().addOnSuccessListener { document ->
                val picture = document.getString("picture")
                if (picture != null) {
                    Glide.with(this)
                        .load(picture)
                        .apply(RequestOptions.circleCropTransform()) // Aplica a transformação de círculo
                        .into(binding.ivReputationProfilePic)
                }
            }
        }

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