package com.kids.teeth.dentista.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.dao.AddressesDao
import com.kids.teeth.dentista.databinding.FragmentAddressesListProfileBinding
import com.kids.teeth.dentista.databinding.FragmentAddressesListSignUpBinding
import com.kids.teeth.dentista.model.Address
import com.kids.teeth.dentista.recyclerview.adapter.AddressesListAdapter
import com.kids.teeth.dentista.recyclerview.adapter.EmergenciesListAdapter

class AddressesListProfileFragment : Fragment() {

    private var _binding: FragmentAddressesListProfileBinding? = null
    private val binding: FragmentAddressesListProfileBinding get() = _binding!!

    var addresses = ArrayList<Address>()

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : AddressesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddressesListProfileBinding
            .inflate(
                inflater,
                container,
                false
            )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadAddressesFirestore()

        adapter = AddressesListAdapter(addresses)

        if (adapter.itemCount == 3)
            binding.fabAddAddress.visibility = View.GONE

        if (addresses.isNotEmpty()){
            recyclerView = binding.rvAddressList
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        Toast.makeText(requireContext(),"AddressListFragment onViewCreated() | Lista: ${AddressesDao.searchAll()}", Toast.LENGTH_LONG).show()

        binding.btnBackAddressesListProfile.setOnClickListener {
            findNavController().navigate(R.id.action_AddressesListProfileFragment_to_ProfileFragment)
        }

        binding.fabAddAddress.setOnClickListener{
            findNavController().navigate(R.id.action_AddressesListProfileFragment_to_AddressRegisterProfileFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()

        db = FirebaseFirestore.getInstance(Firebase.app)
        auth = FirebaseAuth.getInstance(Firebase.app)

        if (addresses.isEmpty()){
            binding.rvAddressList.visibility = View.GONE
            binding.tvTitleEmptyAddresses.visibility = View.VISIBLE
            binding.tvSubtitleEmptyAddresses.visibility = View.VISIBLE
        } else {
            binding.rvAddressList.visibility = View.VISIBLE
            binding.tvTitleEmptyAddresses.visibility = View.GONE
            binding.tvSubtitleEmptyAddresses.visibility = View.GONE
        }

    }

    private fun loadAddressesFirestore(){
        val db = FirebaseFirestore.getInstance(Firebase.app)
        var address: Address

        db.collection("addresses")
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
            }
            .addOnFailureListener { exception ->
                Log.w("AddressesList", "Error getting documents $exception")
            }
    }


}