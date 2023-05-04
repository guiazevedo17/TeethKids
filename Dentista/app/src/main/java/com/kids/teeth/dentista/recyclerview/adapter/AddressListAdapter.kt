package com.kids.teeth.dentista.recyclerview.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.model.Address

class AddressListAdapter(
    addresses: List<Address>
    ) : ListAdapter<Address, AddressListAdapter.AddressViewHolder>(AddressDiffCallBack) {

    private val addresses = addresses.toMutableList()

    class AddressViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name : AppCompatTextView = view.findViewById(R.id.tvAddressName)
        private var currentAddress : Address? = null

        fun bind(a : Address){
            currentAddress = a
            name.text = a.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        Log.d("AddressListAdapter","onCreateViewHolder")
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.address_card,parent,false)
        return AddressViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val a = addresses[position]
        holder.bind(a)
    }

    override fun getItemCount(): Int = addresses.size
}

object AddressDiffCallBack : DiffUtil.ItemCallback<Address>() {
    override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
        return oldItem.name.toString() == newItem.name.toString()
    }
}
