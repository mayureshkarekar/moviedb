package com.moviedb.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.moviedb.MoviesDBApplication
import com.moviedb.R
import com.moviedb.databinding.MovieDetailsDataBinding
import com.moviedb.model.Movie
import com.moviedb.model.Review
import com.moviedb.network.VolleyHelper
import com.moviedb.view.adapter.ReviewsAdapter
import com.moviedb.view.adapter.SimilarMoviesAdapter
import com.moviedb.viewmodel.MovieViewModel
import com.moviedb.viewmodel.MovieViewModelFactory

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {
    private val movieViewModel: MovieViewModel by activityViewModels {
        MovieViewModelFactory((activity?.application as MoviesDBApplication).movieRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        val index = arguments?.getInt("index")
        val movie = movieViewModel.allMovies.value?.get(index!!)

        view?.let {
            movie?.let {
                activity?.title = it.title
                val ivPoster = view.findViewById<ImageView>(R.id.iv_poster)
                val ivBackdrop = view.findViewById<ImageView>(R.id.iv_backdrop)
                Glide.with(view).load(movie.posterPath).into(ivPoster)
                Glide.with(view).load(movie.backdropPath).into(ivBackdrop)

                fetchSimilarMovies(movie)
                fetchReviews(movie)
            }

            val binding = DataBindingUtil.bind<MovieDetailsDataBinding>(view)
            binding?.movie = movie
        }

        return view
    }

    private fun fetchSimilarMovies(movie: Movie) {
        VolleyHelper.getInstance(requireContext()).getSimilarMovies(
            movie.id,
            { response ->
                val moviesList = response.getJSONArray("results")
                val movies = mutableListOf<Movie>()

                for (i in 0 until moviesList.length()) {
                    val currentMovie =
                        Gson().fromJson(
                            moviesList.getJSONObject(i).toString(),
                            Movie::class.java
                        )

                    movies.add(currentMovie)
                }

                val adapter = SimilarMoviesAdapter()
                val recyclerView = view?.findViewById<RecyclerView>(R.id.rv_similar_movies)
                recyclerView?.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                recyclerView?.adapter = adapter
                adapter.submitList(movies)
            },
            { Log.d("TAG", "Error Similar movies: $it") },
        )
    }

    private fun fetchReviews(movie: Movie) {
        VolleyHelper.getInstance(requireContext()).getReviews(movie.id,
            { response ->
                val reviewsList = response.getJSONArray("results")
                val reviews = mutableListOf<Review>()

                for (i in 0 until reviewsList.length()) {
                    val review =
                        Gson().fromJson(
                            reviewsList.getJSONObject(i).toString(),
                            Review::class.java
                        )

                    reviews.add(review)
                }

                val adapter = ReviewsAdapter()
                val recyclerView = view?.findViewById<RecyclerView>(R.id.rv_reviews)
                recyclerView?.layoutManager = LinearLayoutManager(context)
                recyclerView?.addItemDecoration(
                    DividerItemDecoration(
                        context,
                        DividerItemDecoration.VERTICAL
                    )
                )
                recyclerView?.adapter = adapter
                adapter.submitList(reviews)
            },
            { Log.d("TAG", "Error Review: $it") }
        )
    }
}