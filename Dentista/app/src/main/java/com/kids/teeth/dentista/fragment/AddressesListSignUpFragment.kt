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
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.dao.AddressesDao
import com.kids.teeth.dentista.databinding.FragmentAddressesListSignUpBinding
import com.kids.teeth.dentista.model.Address
import com.kids.teeth.dentista.recyclerview.adapter.AddressesListAdapter

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

        val args = this.arguments

        addresses = AddressesDao.searchAll() as ArrayList<Address>
        adapter = AddressesListAdapter(addresses)
        recyclerView = binding.rvAddressList
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter.onItemClick = { address ->
            val index = adapter.getItemIndex(address)

            val bundle = Bundle().apply {
                putString("name", address.name)
                putString("code", address.code)
                putString("street", address.street)
                putString("number", address.number)
                putString("complement", address.complement)
                putString("neighborhood", address.neighborhood)
                putString("city", address.city)
                putString("state", address.state)

                putInt("index", index)
            }

            findNavController().navigate(R.id.action_AddressesListSignUpFragment_to_EditAddressSignUpFragment, bundle)
        }

        if (adapter.itemCount == 3)
            binding.fabAddAddress.visibility = View.GONE


        Toast.makeText(requireContext(),"AddressListFragment onViewCreated() | Lista: ${AddressesDao.searchAll()}", Toast.LENGTH_LONG).show()

        binding.btnBackAddressesListSignUp.setOnClickListener {
            val bundle = passData(args)

            Log.w("AddressesListFragment", "name = ${bundle.getString("name")} | phone = ${bundle.getString("phone")} | email = ${bundle.getString("email")} | password = ${bundle.getString("password")}")

            findNavController().navigate(R.id.action_AddressesListSignUpFragment_to_SignUpFragment, bundle)
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

    private fun passData(args: Bundle?) : Bundle{
        val bundle = Bundle()
        bundle.putString("name", args?.getString("name"))
        bundle.putString("phone", args?.getString("phone"))
        bundle.putString("email", args?.getString("email"))
        bundle.putString("password", args?.getString("password"))
        bundle.putString("confPassword", args?.getString("confPassword"))

        return bundle
    }
}