package com.kids.teeth.dentista.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.databinding.FragmentEmergenciesListBinding
import com.kids.teeth.dentista.model.Emergency


class EmergenciesListFragment : Fragment() {

    private var _binding : FragmentEmergenciesListBinding? = null
    private val binding : FragmentEmergenciesListBinding get() = _binding!!

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

        binding.ibtnEmergenciesList.setOnClickListener {
            findNavController().navigate(R.id.action_EmergenciesListFragment_to_ProfileFragment)
        }
    }

    override fun onStart() {
        super.onStart()

        //loadEmergenciesFirestore()
    }

//    fun loadEmergenciesFirestore(){
//        val db = FirebaseFirestore.getInstance(Firebase.app)
//        var contact: Emergency
//        db.collection("emergencies")
//            .get()
//            .addOnSuccessListener { querySnapshot ->
//                querySnapshot.forEach { document ->
//                    Log.d("ContactsList", "Contato ID ${document.id}")
//
//                    emergency = Emergency(
//                        document.data["requesterName"],
//                        document.data["requesterPhone"])
//
//                    allContacts.add(emergency)
//                }
//                // notificamos que o adapter foi alterado. Com isso recyclerview atualizará os dados
//                contactsAdapter.notifyDataSetChanged()
//            }
//            .addOnFailureListener { exception ->
//                Log.w("EmergenciesList", "Error getting documents $exception")
//            }
//    }

}