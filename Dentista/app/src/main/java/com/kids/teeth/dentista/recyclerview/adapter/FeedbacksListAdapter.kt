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

class FeedbacksListAdapter(private var dataSet: List<Feedback>) :
    ListAdapter<Feedback, FeedbacksListAdapter.FeedbackViewHolder>(FeedbackDiffCallback) {

    var onItemClick : ((Feedback) -> Unit)? = null

    fun updateDataSet(dataSet: List<Feedback>) {
        this.dataSet = dataSet
    }

    class FeedbackViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val requesterName: AppCompatTextView = itemView.findViewById(R.id.tvRequesterName)
        private val feedbackData: AppCompatTextView = itemView.findViewById(R.id.tvFeedbackData)
        private val feedbackComment: AppCompatTextView = itemView.findViewById(R.id.tvFeedbackComment)

        private var currentEmergency: Feedback? = null

        fun bind(e: Feedback) {
            currentEmergency = e
            requesterName.text = capitalizeWords(e.name.toString())
            feedbackData.text = e.date.toString()
            feedbackComment.text = e.comment.toString()
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