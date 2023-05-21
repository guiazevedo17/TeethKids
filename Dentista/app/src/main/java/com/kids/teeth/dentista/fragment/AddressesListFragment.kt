package com.kids.teeth.dentista.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.dao.AddressesDao
import com.kids.teeth.dentista.databinding.FragmentAddressesListBinding
import com.kids.teeth.dentista.recyclerview.adapter.AddressesListAdapter

class AddressesListFragment : Fragment() {

    private var _binding: FragmentAddressesListBinding? = null
    private val binding: FragmentAddressesListBinding get() = _binding!!

    private val arguments by navArgs<AddressesListFragmentArgs>()

    private lateinit var addressAdapter : AddressesListAdapter

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

        val addressAdapter = AddressesListAdapter(AddressesDao.searchAll())

        binding.rvAddressList.adapter = addressAdapter

        Toast.makeText(requireContext(),"AddressListFragment onViewCreated() getItemCount()- ${addressAdapter.itemCount} | Lista: ${AddressesDao.searchAll()}", Toast.LENGTH_LONG).show()

        binding.ibtnBackAddressList.setOnClickListener {
            // funciona quando enquanto não cadastra novo endereço enquanto logado!

            if (arguments.loggedIn)
                findNavController().navigate(R.id.action_AddressesListFragment_to_ProfileFragment)
            else
                findNavController().navigate(R.id.action_AddressesListFragment_to_SignUpFragment)
        }

        binding.fabAddAddress.setOnClickListener{
            findNavController().navigate(R.id.action_AddressesListFragment_to_AddressRegisterFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}