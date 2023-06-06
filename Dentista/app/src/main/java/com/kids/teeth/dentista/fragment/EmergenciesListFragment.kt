package com.kids.teeth.dentista.fragment

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.databinding.FragmentEmergenciesListBinding
import com.kids.teeth.dentista.model.Emergency
import com.kids.teeth.dentista.recyclerview.adapter.EmergenciesListAdapter


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

        Log.d("EmergenciesListFragment", "Binding is null: ${binding == null}")
    }

    override fun onStart() {
        super.onStart()

        loadEmergenciesFirestore()
    }

    private fun loadEmergenciesFirestore(){
        val db = FirebaseFirestore.getInstance(Firebase.app)
        var emergency: Emergency

        db.collection("teste_emergencia")
            .get()
            .addOnSuccessListener { querySnapshot ->
                querySnapshot.forEach { document ->
                    val name = document.data["name"] as? String

                    if (name != null) {
                        emergency = Emergency(name)

                        emergencies.add(emergency)
                        adapter.notifyDataSetChanged()
                    } else{
                        Log.w("EmergenciesList", "Emergency name is null!")
                    }

                }
                // notificamos que o adapter foi alterado. Com isso recyclerview atualizará os dados
            }
            .addOnFailureListener { exception ->
                Log.w("EmergenciesList", "Error getting documents $exception")
            }
    }

}