package com.kids.teeth.dentista.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.dao.AddressesDao
import com.kids.teeth.dentista.model.Emergency
import com.kids.teeth.dentista.databinding.FragmentFeedbacksListBinding
import com.kids.teeth.dentista.model.Action
import com.kids.teeth.dentista.model.Address
import com.kids.teeth.dentista.model.Feedback
import com.kids.teeth.dentista.recyclerview.adapter.AddressesListAdapter
import com.kids.teeth.dentista.recyclerview.adapter.FeedbacksListAdapter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class FeedbacksListFragment : Fragment() {

    private var _binding: FragmentFeedbacksListBinding? = null
    private val binding: FragmentFeedbacksListBinding get() = _binding!!

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FeedbacksListAdapter

    val feedbacks = ArrayList<Feedback>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedbacksListBinding
            .inflate(
                inflater,
                container,
                false
            )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FeedbacksListAdapter(ArrayList())

        db = FirebaseFirestore.getInstance(Firebase.app)
        auth = FirebaseAuth.getInstance(Firebase.app)

        loadFeedbacksFirestore()

        recyclerView = binding.rvFeedbacksList
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.btnBackFeedbacksList.setOnClickListener {
            findNavController().navigate(R.id.action_FeedbacksListFragment_to_ReputationProfileFragment)
        }

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

                            Log.w("FeedbacksListFragment", "id = $id | name = $requesterName | date = $date | comment = $comment | rating = $rating ")

                        }
                    }
                    adapter.updateDataSet(feedbacks)
                    adapter.notifyDataSetChanged()
                    Log.w("FeedbacksListFragment", "feedbacks - $feedbacks")
                }
        }
    }



}