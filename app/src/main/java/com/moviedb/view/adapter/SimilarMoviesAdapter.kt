package com.moviedb.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.moviedb.R
import com.moviedb.model.Movie
import com.moviedb.network.VolleyHelper

class SimilarMoviesAdapter :
    ListAdapter<Movie, SimilarMoviesAdapter.MovieViewHolder>(MoviesComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(holder.itemView.context, movie)
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        private val ivPoster: ImageView = itemView.findViewById(R.id.iv_poster)

        fun bind(context: Context, movie: Movie?) {
            movie?.let {
                tvTitle.text = movie.title
                Glide.with(context).load(VolleyHelper.IMAGES_URL + movie.posterPath).into(ivPoster)
            }
        }

        companion object {
            fun create(parent: ViewGroup): MovieViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_similar_movies_list_item, parent, false)
                return MovieViewHolder(view)
            }
        }
    }

    class MoviesComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
    }
}