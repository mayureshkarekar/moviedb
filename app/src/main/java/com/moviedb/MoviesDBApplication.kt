package com.moviedb

import android.app.Application
import com.moviedb.database.MovieDatabase
import com.moviedb.database.MovieRepository

class MoviesDBApplication : Application() {
    private val movieDatabase by lazy { MovieDatabase.getDatabase(this) }
    val movieRepository by lazy { MovieRepository(movieDatabase.movieDao()) }
}