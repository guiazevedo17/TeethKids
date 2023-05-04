package com.kids.teeth.dentista.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.dao.AddressesDao
import com.kids.teeth.dentista.databinding.FragmentAddressListBinding
import com.kids.teeth.dentista.recyclerview.adapter.AddressListAdapter

class AddressListFragment : Fragment() {

    private var _binding: FragmentAddressListBinding? = null
    private val binding: FragmentAddressListBinding get() = _binding!!

    private lateinit var addressAdapter : AddressListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddressListBinding
            .inflate(
                inflater,
                container,
                false
            )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            val addressAdapter = AddressListAdapter(AddressesDao.searchAll())

            binding.rvAddressList.adapter = addressAdapter

            Toast.makeText(requireContext(),"AddressListFragment onViewCreated() getItemCount()- ${addressAdapter.itemCount} | Lista: ${AddressesDao.searchAll()}", Toast.LENGTH_LONG).show()


        binding.fabAddAddress.setOnClickListener{
            findNavController().navigate(R.id.action_AddressListFragment_to_AddressRegisterFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}