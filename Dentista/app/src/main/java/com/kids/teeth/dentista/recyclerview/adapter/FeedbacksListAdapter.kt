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
import com.kids.teeth.dentista.model.Feedback
import java.util.Locale

class FeedbacksListAdapter(private val dataSet: List<Emergency>) :
    ListAdapter<Feedback, FeedbacksListAdapter.FeedbackViewHolder>(FeedbackDiffCallback) {

    var onItemClick : ((Emergency) -> Unit)? = null

    class FeedbackViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val requesterName: AppCompatTextView = itemView.findViewById(R.id.tvEmergencyRequesterName)
        private var currentEmergency: Emergency? = null

        fun bind(e: Emergency) {
            currentEmergency = e
            requesterName.text = capitalizeWords(e.name.toString())
        }

        private fun capitalizeWords(requesterName: String): CharSequence? {
            val words = requesterName.split(" ")
            val capitalizedWords = words.map { it.capitalize(Locale.ROOT) }
            return capitalizedWords.joinToString(" ")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.feedback_card, parent, false)
        return FeedbackViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedbackViewHolder, position: Int) {
        val e = dataSet[position]
        holder.bind(e)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(e)
        }
    }

    override fun getItemCount() = dataSet.size
}

object FeedbackDiffCallback : DiffUtil.ItemCallback<Feedback>() {
    override fun areItemsTheSame(oldItem: Feedback, newItem: Feedback): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Feedback, newItem: Feedback): Boolean {
        return oldItem.name.toString() == newItem.name.toString()
    }
}