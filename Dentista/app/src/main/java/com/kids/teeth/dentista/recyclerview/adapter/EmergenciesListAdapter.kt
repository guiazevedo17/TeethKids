package com.kids.teeth.dentista.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.model.Emergency

class EmergenciesListAdapter(private val dataSet: List<Emergency>) :
    ListAdapter<Emergency, EmergenciesListAdapter.EmergencyViewHolder>(EmergencyDiffCallback) {

    class EmergencyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val requesterName: AppCompatTextView = itemView.findViewById(R.id.tvEmergencyRequesterName)
        private var currentEmergency: Emergency? = null

        fun bind(e: Emergency) {
            currentEmergency = e
            requesterName.text = e.name.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmergencyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.emergency_card, parent, false)
        return EmergencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmergencyViewHolder, position: Int) {
        val e = dataSet[position]
        holder.bind(e)
    }

    override fun getItemCount() = dataSet.size
}

object EmergencyDiffCallback : DiffUtil.ItemCallback<Emergency>() {
    override fun areItemsTheSame(oldItem: Emergency, newItem: Emergency): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Emergency, newItem: Emergency): Boolean {
        return oldItem.name.toString() == newItem.name.toString()
    }
}