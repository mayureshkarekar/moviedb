package com.moviedb.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.moviedb.MoviesDBApplication
import com.moviedb.R
import com.moviedb.model.Movie
import com.moviedb.network.VolleyHelper
import com.moviedb.view.fragment.MainFragment
import com.moviedb.view.fragment.SearchMovieFragment
import com.moviedb.viewmodel.MovieViewModel
import com.moviedb.viewmodel.MovieViewModelFactory

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModelFactory((application as MoviesDBApplication).movieRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setSupportActionBar(findViewById(R.id.tb_main))
        fetchMoviesPlayingNow()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_search -> replaceFragment(SearchMovieFragment::class.java)
        }

        return true
    }

    override fun onBackPressed() {
        when (supportFragmentManager.findFragmentById(R.id.fcv_main)) {
            is MainFragment -> super.onBackPressed()
            else -> replaceFragment(MainFragment::class.java)
        }
    }

    fun replaceFragment(fragmentClass: Class<out Fragment?>, args: Bundle? = null) {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fcv_main, fragmentClass, args)
            .commit()

        if (fragmentClass == SearchMovieFragment::class.java)
            supportActionBar?.hide()
        else
            supportActionBar?.show()
    }

    private fun fetchMoviesPlayingNow() {
        VolleyHelper.getInstance(this).getAllMoviesPlayingNow(
            { response ->
                val moviesList = response.getJSONArray("results")

                for (i in 0 until moviesList.length()) {
                    val movie =
                        Gson().fromJson(
                            moviesList.getJSONObject(i).toString(),
                            Movie::class.java
                        )

                    movieViewModel.insertMovie(movie)
                }
            },
            { Log.e(TAG, "Failed to fetch the data from API.") })
    }
}