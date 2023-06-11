package com.kids.teeth.dentista.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.dao.AddressesDao
import com.kids.teeth.dentista.databinding.FragmentAddressesListBinding
import com.kids.teeth.dentista.model.Address
import com.kids.teeth.dentista.model.Emergency
import com.kids.teeth.dentista.recyclerview.adapter.AddressesListAdapter
import com.kids.teeth.dentista.recyclerview.adapter.EmergenciesListAdapter

class AddressesListFragment : Fragment() {

    private var _binding: FragmentAddressesListBinding? = null
    private val binding: FragmentAddressesListBinding get() = _binding!!

    var addresses = ArrayList<Address>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : AddressesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddressesListBinding
            .inflate(
                inflater,
                container,
                false
            )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (addresses.isNotEmpty()){
            recyclerView = binding.rvAddressList
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        Toast.makeText(requireContext(),"AddressListFragment onViewCreated() | Lista: ${AddressesDao.searchAll()}", Toast.LENGTH_LONG).show()

        binding.fabAddAddress.setOnClickListener{
            findNavController().navigate(R.id.action_AddressesListFragment_to_AddressRegisterFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        recyclerView.adapter = adapter

        loadAddressesFirestore()

    }

    private fun loadAddressesFirestore(){
        val db = FirebaseFirestore.getInstance(Firebase.app)
        var address: Address

        db.collection("dentists")
            .get()
            .addOnSuccessListener { querySnapshot ->
                querySnapshot.forEach { document ->
                    //Log.d("ContactsList", "Contato ID ${document.id}")
                    val name = document.data["name"] as? String

                    if (name != null) {
                        address = Address(name)

                        addresses.add(address)
                        adapter.notifyDataSetChanged()
                    } else{
                        Log.w("AddressesList", "Address name is null!")
                    }

                }
                // notificamos que o adapter foi alterado. Com isso recyclerview atualizará os dados
            }
            .addOnFailureListener { exception ->
                Log.w("AddressesList", "Error getting documents $exception")
            }
    }

}