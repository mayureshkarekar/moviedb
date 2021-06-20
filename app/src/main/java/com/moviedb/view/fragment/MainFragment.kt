package com.moviedb.view.fragment

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviedb.MoviesDBApplication
import com.moviedb.R
import com.moviedb.view.adapter.MovieListAdapter
import com.moviedb.viewmodel.MovieViewModel
import com.moviedb.viewmodel.MovieViewModelFactory


class MainFragment : Fragment(R.layout.fragment_main) {
    private val movieViewModel: MovieViewModel by activityViewModels {
        MovieViewModelFactory((activity?.application as MoviesDBApplication).movieRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = getString(R.string.now_playing)

        val adapter = MovieListAdapter(activity as Activity)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_main)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecoration(context, VERTICAL))
        recyclerView.adapter = adapter

        movieViewModel.allMovies.observe(viewLifecycleOwner, { movies ->
            movies?.let { adapter.submitList(it) }
        })
    }
}