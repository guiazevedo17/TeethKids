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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.model.Emergency
import com.kids.teeth.dentista.databinding.FragmentEmergenciesListBinding
import com.kids.teeth.dentista.recyclerview.adapter.EmergenciesListAdapter
import java.text.SimpleDateFormat
import java.util.Locale


class EmergenciesListFragment : Fragment() {

    private var _binding : FragmentEmergenciesListBinding? = null
    private val binding : FragmentEmergenciesListBinding get() = _binding!!

    val emergencies = ArrayList<Emergency>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : EmergenciesListAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmergenciesListBinding
            .inflate(
                inflater,
                container,
                false
            )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.rvEmergenciesList
        adapter = EmergenciesListAdapter(emergencies)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter.onItemClick = { item ->
            val bundle = Bundle()
            bundle.putString("name", item.name)
            bundle.putString("phone", item.phone)
            bundle.putString("date", item.date)
            bundle.putStringArrayList("images", item.images as? ArrayList<String>)

            findNavController().navigate(R.id.action_EmergenciesListFragment_to_EmergencyDetailFragment, bundle)
        }

        binding.btnBackEmergenciesList.setOnClickListener {
            findNavController().navigate(R.id.action_EmergenciesListFragment_to_ProfileFragment)
        }

    }

    override fun onStart() {
        super.onStart()

        loadEmergenciesFirestore()
    }

    private fun loadEmergenciesFirestore(){
        val db = FirebaseFirestore.getInstance(Firebase.app)
        var emergency: Emergency

        db.collection("emergencies")
            .get()
            .addOnSuccessListener { querySnapshot ->
                querySnapshot.forEach { document ->
                    val name = document.data["name"] as? String
                    val phone = document.data["phone"] as? String

                    val images = document.data["images"] as? ArrayList<String>

                    Log.w("EmergenciesList", "loadEmergencies - images - $images")

                    val timestamp = document.data["data"] as? com.google.firebase.Timestamp
                    val date = timestamp?.toDate()

                    if (name != null && phone != null && date != null && images != null) {
                        val sdf = SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault())
                        val formattedDate = sdf.format(date)

                        emergency = Emergency(name, phone, formattedDate, images)

                        emergencies.add(emergency)
                        adapter.notifyDataSetChanged()
                    } else{
                        Log.w("EmergenciesList", "Some fields are null: name=$name, phone=$phone, date=$date, images=$images")
                    }

                }
                // notificamos que o adapter foi alterado. Com isso recyclerview atualizará os dados
            }
            .addOnFailureListener { exception ->
                Log.w("EmergenciesList", "Error getting documents $exception")
            }
    }

}