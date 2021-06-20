package com.moviedb.view.fragment

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviedb.MoviesDBApplication
import com.moviedb.R
import com.moviedb.view.adapter.MovieListAdapter
import com.moviedb.view.adapter.RecentSearchAdapter
import com.moviedb.viewmodel.MovieViewModel
import com.moviedb.viewmodel.MovieViewModelFactory
import java.util.*

class SearchMovieFragment : Fragment(R.layout.fragment_search_movie) {
    private val movieViewModel: MovieViewModel by activityViewModels {
        MovieViewModelFactory((activity?.application as MoviesDBApplication).movieRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchAdapter = RecentSearchAdapter()
        setRecentSearchList(view, searchAdapter)

        val movieListAdapter = MovieListAdapter(activity as Activity)
        setSearchedMovieList(view, movieListAdapter)

        movieViewModel.allMovies.observe(viewLifecycleOwner, { movies ->
            movies?.let { movieListAdapter.submitList(it) }
        })

        addTextChangedListener(view, movieViewModel, searchAdapter, movieListAdapter)
    }
}

private fun setRecentSearchList(view: View, searchAdapter: RecentSearchAdapter) {
    val rvRecentSearch = view.findViewById<RecyclerView>(R.id.rv_recent_searches)
    rvRecentSearch.layoutManager =
        LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
    rvRecentSearch.adapter = searchAdapter
}

private fun setSearchedMovieList(view: View, movieListAdapter: MovieListAdapter) {
    val rvMovieList = view.findViewById<RecyclerView>(R.id.rv_movies)
    rvMovieList.layoutManager = LinearLayoutManager(view.context)
    rvMovieList.addItemDecoration(
        DividerItemDecoration(
            view.context,
            DividerItemDecoration.VERTICAL
        )
    )
    rvMovieList.adapter = movieListAdapter
}

private fun addTextChangedListener(
    view: View,
    movieViewModel: MovieViewModel,
    recentSearchAdapter: RecentSearchAdapter,
    movieListAdapter: MovieListAdapter
) {
    val input = view.findViewById<EditText>(R.id.et_search_box)
    input.addTextChangedListener {
        val text = input.text.toString()

        val movies = movieViewModel.allMovies.value?.filter { movie ->
            movie.title!!.toLowerCase(Locale.ROOT).startsWith(text.toLowerCase(Locale.ROOT))
        }

        if (movies?.size == 1) {
            movieViewModel.recentlySearchedMovies.remove(movies[0])
            movieViewModel.recentlySearchedMovies.add(movies[0])
        }

        if (movieViewModel.recentlySearchedMovies.size > 0) {
            view.findViewById<TextView>(R.id.tv_recent_searches_title).visibility = View.VISIBLE
            view.findViewById<RecyclerView>(R.id.rv_recent_searches).visibility = View.VISIBLE
        } else {
            view.findViewById<TextView>(R.id.tv_recent_searches_title).visibility = View.GONE
            view.findViewById<RecyclerView>(R.id.rv_recent_searches).visibility = View.GONE
        }

        movieListAdapter.submitList(movies)
        movieListAdapter.notifyDataSetChanged()

        recentSearchAdapter.submitList(movieViewModel.recentlySearchedMovies.toList())
        recentSearchAdapter.notifyDataSetChanged()
    }
}