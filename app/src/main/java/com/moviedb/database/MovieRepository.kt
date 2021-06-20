package com.moviedb.database

import com.moviedb.model.MovieDao
import androidx.annotation.WorkerThread
import com.moviedb.model.Movie
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {
    val allMovies: Flow<List<Movie>> = movieDao.fetchAllMovies()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertMovie(movie: Movie) {
        movieDao.insertMovie(movie)
    }
}