package com.kids.teeth.dentista.fragment

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
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
import com.kids.teeth.dentista.model.Feedback
import java.text.SimpleDateFormat
import java.util.Locale

class ReputationProfileFragment : Fragment() {

    private var _binding: FragmentReputationProfileBinding? = null
    private val binding: FragmentReputationProfileBinding get() = _binding!!

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    val feedbacks = ArrayList<Feedback>()

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

        loadFeedbacksFirestore()

        binding.btnBackReputation.setOnClickListener {
            findNavController().navigate(R.id.action_ReputationProfileFragment_to_ProfileFragment)
        }

        binding.btnReputationFeedbacks.setOnClickListener {
            findNavController().navigate(R.id.action_ReputationProfileFragment_to_FeedbacksListFragment)
        }
    }

    private fun setRate() {
        Log.w("ReputationProfileFragment", "setRate() feedbacks - $feedbacks")

        var rateQntd: Int = 0
        var rateTotal: Int = 0
        val average: Double?

        for (feedback in feedbacks){
            rateQntd++

            rateTotal += feedback.rating!!.toInt()

            Log.w("ReputationProfileFragment", "Casos - $rateQntd | Soma - $rateTotal")
        }

        average = Math.round((rateTotal/rateQntd).toDouble()).toDouble()

        val imageBindings = listOf(binding.ivReputationStar1, binding.ivReputationStar2, binding.ivReputationStar3, binding.ivReputationStar4, binding.ivReputationStar5)

        val averageFor = average.toInt() -1

        for (i in 0.. averageFor){ // Troca as Imagens de Estrela
            val imageView = imageBindings.get(i)

            imageView.setImageResource(R.drawable.ic_star_on)
        }

        binding.tvReputationCasesQntd.text = rateQntd.toString()

        binding.tvReputationRank.text = average.toString()


    }

    private fun loadFeedbacksFirestore() {
        feedbacks.clear()

        var feedback: Feedback

        val currentUser = auth.currentUser

        if (currentUser != null) {
            val uid = currentUser.uid
            db.collection("reviews")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    querySnapshot.forEach { document ->
                        val id = document["id"] as String?
                        val requesterName = document["name"] as String?

                        val timestamp = document["date"] as? com.google.firebase.Timestamp
                        val date = timestamp?.toDate()

                        val comment = document["message"] as String?

                        val rating = document["rating"] as? Number?

                        if (id != null && requesterName != null && date != null && comment != null && rating != null){
                            val sdf = SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault())
                            val formattedDate = sdf.format(date) as String?

                            feedback = Feedback(id, requesterName, formattedDate, comment, rating)

                            feedbacks.add(feedback)
                            binding.tvReputationName.text = requesterName
                            Log.w("ReputationProfileFragment", "id = $id | name = $requesterName | date = $formattedDate | comment = $comment | rating = $rating ")

                        }
                    }
                    setRate()

                }
        }
    }


}