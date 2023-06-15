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
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.model.Emergency
import com.kids.teeth.dentista.databinding.FragmentEmergenciesListBinding
import com.kids.teeth.dentista.model.Action
import com.kids.teeth.dentista.recyclerview.adapter.EmergenciesListAdapter
import java.text.SimpleDateFormat
import java.util.Locale


class EmergenciesListFragment : Fragment() {

    private var _binding: FragmentEmergenciesListBinding? = null
    private val binding: FragmentEmergenciesListBinding get() = _binding!!

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    val avaiable = ArrayList<String>()
    val actioned = ArrayList<String>()


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EmergenciesListAdapter


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

        adapter = EmergenciesListAdapter(ArrayList())

        db = FirebaseFirestore.getInstance(Firebase.app)
        auth = FirebaseAuth.getInstance(Firebase.app)

        updateEmergenciesList()
        setRecyclerView()

        binding.btnBackEmergenciesList.setOnClickListener {
            findNavController().navigate(R.id.action_EmergenciesListFragment_to_ProfileFragment)
        }

    }

    private fun setRecyclerView() {
        recyclerView = binding.rvEmergenciesList
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter.onItemClick = { item ->
            val bundle = Bundle()
            bundle.putString("fcmToken", item.fcmtoken)
            bundle.putString("emergencyId", item.id)
            bundle.putString("name", item.name)
            bundle.putString("phone", item.phone)
            bundle.putString("date", item.date)
            bundle.putStringArrayList("images", item.images as? ArrayList<String>)

            findNavController().navigate(
                R.id.action_EmergenciesListFragment_to_EmergencyDetailFragment,
                bundle
            )
        }
    }

    override fun onResume() {
        super.onResume()

        updateEmergenciesList()
    }

    private fun updateEmergenciesList() {
        var emergency:Emergency
        var actions: Action

        var avaiableEmergencies = ArrayList<Emergency>()
        val actionedEmergencies = ArrayList<Action>()
        var emergencies = ArrayList<Emergency>()

        Log.w("EmergenciesList","auth -  ${auth.currentUser?.uid}")

        val avaiableEmergenciesTask = db.collection("emergencies").get()
        val actionedEmergenciesTask = db.collection("actions")
            .whereEqualTo("dentistId", auth.currentUser?.uid)
            .get()

        Tasks.whenAllSuccess<QuerySnapshot>(avaiableEmergenciesTask, actionedEmergenciesTask)
            .addOnCompleteListener { tasks ->
                if (!tasks.isSuccessful) return@addOnCompleteListener

                val avaiableEmergenciesSnapshot = tasks.result[0] as QuerySnapshot
                val actionedEmergenciesSnapshot = tasks.result[1] as QuerySnapshot

                for (document in avaiableEmergenciesSnapshot) {
                    // process avaiableEmergencies
                    val fcmToken = document.data["fcmToken"] as? String
                    val id = document.data["userId"] as? String

                    val name = document.data["name"] as? String
                    val phone = document.data["phone"] as? String

                    val images = document.data["images"] as? ArrayList<String>

                    val timestamp = document.data["data"] as? com.google.firebase.Timestamp
                    val date = timestamp?.toDate()

                    if (fcmToken != null && id != null && name != null && phone != null && date != null && images != null) {
                        val sdf = SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault())
                        val formattedDate = sdf.format(date)

                        emergency = Emergency(fcmToken, id, name, phone, formattedDate, images)

                        emergencies.add(emergency)

                        Log.w(
                            "EmergenciesList",
                            "loadAvaiableEmergenciesFirestore $avaiableEmergencies"
                        )

                    } else {
                        Log.w(
                            "EmergenciesList",
                            "Some fields are null: name=$name, phone=$phone, date=$date, images=$images"
                        )
                    }
                }

                for (document in actionedEmergenciesSnapshot) {
                    // process actionedEmergencies
                    val dentistId = document.data["dentistId"] as? String
                    val emergencyId = document.data["emergencyId"] as? String
                    val action = document.data["action"] as? String

                    if (dentistId != null && emergencyId != null && action != null) {

                        actions = Action(dentistId, emergencyId, action)

                        actionedEmergencies.add(actions)
                        Log.w(
                            "EmergenciesList",
                            "loadActionedEmergenciesFirestore $actionedEmergencies"
                        )
                    } else {
                        Log.w(
                            "EmergenciesList",
                            "Some fields are null: dentistId=$dentistId, emergencyId=$emergencyId, action=$action"
                        )
                    }
                }

                val actionEmergenciesIds = actionedEmergencies.map { actionedEmergency ->  actionedEmergency.emergencyId }

                avaiableEmergencies = emergencies.filter { emergency -> !actionEmergenciesIds.contains(emergency.id) } as ArrayList<Emergency>

                adapter.updateDataSet(avaiableEmergencies)
                adapter.notifyDataSetChanged()

            }
    }


}