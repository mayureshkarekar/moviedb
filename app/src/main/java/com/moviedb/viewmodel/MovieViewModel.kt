package com.moviedb.viewmodel

import androidx.lifecycle.*
import com.moviedb.database.MovieRepository
import com.moviedb.model.Movie
import kotlinx.coroutines.launch
import java.util.*

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    val allMovies: LiveData<List<Movie>> = movieRepository.allMovies.asLiveData()
    val recentlySearchedMovies: Queue<Movie> = LinkedList()

    fun insertMovie(movie: Movie) = viewModelScope.launch {
        movieRepository.insertMovie(movie)
    }
}

class MovieViewModelFactory(private val movieRepository: MovieRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(movieRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}