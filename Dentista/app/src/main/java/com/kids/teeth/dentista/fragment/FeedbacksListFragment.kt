package com.kids.teeth.dentista.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.model.Emergency
import com.kids.teeth.dentista.databinding.FragmentFeedbacksListBinding
import com.kids.teeth.dentista.model.Feedback
import com.kids.teeth.dentista.recyclerview.adapter.FeedbacksListAdapter
import java.text.SimpleDateFormat
import java.util.Locale


class FeedbacksListFragment : Fragment() {

    private var _binding : FragmentFeedbacksListBinding? = null
    private val binding : FragmentFeedbacksListBinding get() = _binding!!

    val feedbacks = ArrayList<Feedback>()

//    private lateinit var recyclerView: RecyclerView
//    private lateinit var adapter : FeedbacksListAdapter


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


//        if (feedbacks.isNotEmpty()){
//            recyclerView = binding.rvFeedbacksList
//            recyclerView.adapter = adapter
//            recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        }

        binding.btnBackFeedbacksList.setOnClickListener {
            findNavController().navigate(R.id.action_FeedbacksListFragment_to_ReputationProfileFragment)
        }

    }

    override fun onStart() {
        super.onStart()

    }


}