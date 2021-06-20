package com.moviedb.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moviedb.R
import com.moviedb.model.Review

class ReviewsAdapter() :
    ListAdapter<Review, ReviewsAdapter.ReviewViewHolder>(ReviewsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tv_name)
        private val tvContent: TextView = itemView.findViewById(R.id.tv_content)

        fun bind(review: Review?) {
            review?.let {
                tvName.text = review.author
                tvContent.text = review.content
            }
        }

        companion object {
            fun create(parent: ViewGroup): ReviewViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_review_list_item, parent, false)
                return ReviewViewHolder(view)
            }
        }
    }

    class ReviewsComparator : DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem.id == newItem.id
        }
    }
}