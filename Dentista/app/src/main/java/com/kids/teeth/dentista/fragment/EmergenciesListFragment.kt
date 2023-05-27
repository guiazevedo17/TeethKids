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

    private val adapter : EmergenciesListAdapter = EmergenciesListAdapter(emergencies)


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

        Log.d("EmergenciesListFragment", "Binding is null: ${binding == null}")


        val btnBorder = GradientDrawable()
        btnBorder.setColor(ContextCompat.getColor(requireContext(), R.color.tk_blue))
        btnBorder.setStroke(6, ContextCompat.getColor(requireContext(),R.color.tk_blue))
        btnBorder.cornerRadius = 30f

        binding.btnEmergenciesHistoric.background = btnBorder

        binding.btnEmergenciesHistoric.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, EmergenciesHistoricFragment())
                .commit()
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
                    //Log.d("ContactsList", "Contato ID ${document.id}")

                     emergency = Emergency(
                        document.data["name"] as String
                    )

                    emergencies.add(emergency)
                }
                // notificamos que o adapter foi alterado. Com isso recyclerview atualizará os dados
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w("EmergenciesList", "Error getting documents $exception")
            }
    }

}