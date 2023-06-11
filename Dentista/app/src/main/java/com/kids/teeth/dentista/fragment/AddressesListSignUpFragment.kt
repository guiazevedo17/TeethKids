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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.dao.AddressesDao
import com.kids.teeth.dentista.databinding.FragmentAddressesListSignUpBinding
import com.kids.teeth.dentista.model.Address
import com.kids.teeth.dentista.recyclerview.adapter.AddressesListAdapter
import com.kids.teeth.dentista.recyclerview.adapter.EmergenciesListAdapter

class AddressesListSignUpFragment : Fragment() {

    private var _binding: FragmentAddressesListSignUpBinding? = null
    private val binding: FragmentAddressesListSignUpBinding get() = _binding!!

    var addresses = ArrayList<Address>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : AddressesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddressesListSignUpBinding
            .inflate(
                inflater,
                container,
                false
            )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addresses = AddressesDao.searchAll() as ArrayList<Address>
        adapter = AddressesListAdapter(addresses)

        if (addresses.isNotEmpty()){
            recyclerView = binding.rvAddressList
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            adapter.onItemClick = { address ->
                val bundle = Bundle().apply {
                    putString("name", address.name)
                }
                Log.i("AddressesListSignUp", "addressName = ${address.name}")
                findNavController().navigate(R.id.action_AddressesListSignUpFragment_to_EditAddressSignUpFragment, bundle)
            }
        }

        Toast.makeText(requireContext(),"AddressListFragment onViewCreated() | Lista: ${AddressesDao.searchAll()}", Toast.LENGTH_LONG).show()

        binding.btnBackAddressesListSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_AddressesListSignUpFragment_to_SignUpFragment)
        }

        binding.fabAddAddress.setOnClickListener{
            findNavController().navigate(R.id.action_AddressesListSignUpFragment_to_AddressRegisterSignUpFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()

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
}