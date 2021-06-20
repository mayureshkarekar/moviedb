package com.moviedb.view.adapter

import android.app.Activity
import android.content.Context
import android.os.Bundle
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
import com.moviedb.view.MainActivity
import com.moviedb.view.fragment.MovieDetailsFragment

class MovieListAdapter(private val activity: Activity) :
    ListAdapter<Movie, MovieListAdapter.MovieViewHolder>(MoviesComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(holder.itemView.context, movie)
        holder.btnBook.setOnClickListener {
            val args = Bundle().apply {
                putInt("index", position)
            }

            (activity as MainActivity).replaceFragment(MovieDetailsFragment::class.java, args)
        }
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        private val tvReleaseDate: TextView = itemView.findViewById(R.id.tv_release_date)
        private val tvVotes: TextView = itemView.findViewById(R.id.tv_votes)
        private val ivPoster: ImageView = itemView.findViewById(R.id.iv_poster)
        val btnBook: TextView = itemView.findViewById(R.id.tv_book)

        fun bind(context: Context, movie: Movie?) {
            movie?.let {
                tvTitle.text = movie.title
                tvReleaseDate.text = context.getString(R.string.releasing_on, movie.releaseDate)
                tvVotes.text = movie.voteAverage.toString()
                Glide.with(context).load(movie.posterPath).into(ivPoster)
            }
        }

        companion object {
            fun create(parent: ViewGroup): MovieViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_movie_list_item, parent, false)
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