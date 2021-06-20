package com.moviedb.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class VolleyHelper private constructor(context: Context) {
    companion object {
        const val API_KEY = "0ae9e29f7192c4a0c56e0772ef78751b"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val MOVIES_PLAYING_NOW_URL = "${BASE_URL}movie/now_playing?api_key=$API_KEY"
        const val IMAGES_URL = "https://image.tmdb.org/t/p/w500"

        @Volatile
        private var INSTANCE: VolleyHelper? = null

        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: VolleyHelper(context).also { INSTANCE = it }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    private fun <T> addToRequestQueue(request: Request<T>) = requestQueue.add(request)

    fun cancelAll(tag: String) = requestQueue.cancelAll(tag)

    fun getAllMoviesPlayingNow(
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ) {
        fetchStudentsUsingGET(MOVIES_PLAYING_NOW_URL, responseListener, errorListener)
    }

    fun getSimilarMovies(
        movieId: Int,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ) {
        val SIMILAR_MOVIES_URL = "$BASE_URL/movie/$movieId/similar?api_key=$API_KEY"
        fetchStudentsUsingGET(SIMILAR_MOVIES_URL, responseListener, errorListener)
    }

    fun getReviews(
        movieId: Int,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ) {
        val MOVIE_REVIEWS_URL = "$BASE_URL/movie/$movieId/reviews?api_key=$API_KEY"
        Log.d("TAG", "getReviews: $MOVIE_REVIEWS_URL")
        fetchStudentsUsingGET(MOVIE_REVIEWS_URL, responseListener, errorListener)
    }

    private fun fetchStudentsUsingGET(
        url: String, responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ) {
        val studentsRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            responseListener,
            errorListener
        ).setTag(url)

        cancelAll(url)
        addToRequestQueue(studentsRequest)
    }
}