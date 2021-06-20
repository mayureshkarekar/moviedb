package com.moviedb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.moviedb.model.Movie.Companion.TABLE_NAME
import com.moviedb.network.VolleyHelper

@Entity(tableName = TABLE_NAME)
data class Movie(@SerializedName("id") @PrimaryKey @ColumnInfo(name = COLUMN_ID) val id: Int) {
    @SerializedName(COLUMN_ADULT)
    @ColumnInfo(name = COLUMN_ADULT)
    var isAdult: Boolean = false

    @SerializedName(COLUMN_BACKDROP_PATH)
    @ColumnInfo(name = COLUMN_BACKDROP_PATH)
    var backdropPath: String? = null
        set(value) {
            value?.let {
                field = VolleyHelper.IMAGES_URL + value
            }
        }

    @SerializedName(COLUMN_ORIGINAL_LANGUAGE)
    @ColumnInfo(name = COLUMN_ORIGINAL_LANGUAGE)
    var originalLanguage: String? = null

    @SerializedName(COLUMN_ORIGINAL_TITLE)
    @ColumnInfo(name = COLUMN_ORIGINAL_TITLE)
    var originalTitle: String? = null

    @SerializedName(COLUMN_OVERVIEW)
    @ColumnInfo(name = COLUMN_OVERVIEW)
    var overview: String? = null

    @SerializedName(COLUMN_POPULARITY)
    @ColumnInfo(name = COLUMN_POPULARITY)
    var popularity: Double = 0.0

    @SerializedName(COLUMN_POSTER_PATH)
    @ColumnInfo(name = COLUMN_POSTER_PATH)
    var posterPath: String? = null
        set(value) {
            value?.let {
                field = VolleyHelper.IMAGES_URL + value
            }
        }

    @SerializedName(COLUMN_RELEASE_DATE)
    @ColumnInfo(name = COLUMN_RELEASE_DATE)
    var releaseDate: String? = null

    @SerializedName(COLUMN_TITLE)
    @ColumnInfo(name = COLUMN_TITLE)
    var title: String? = null

    @SerializedName(COLUMN_VIDEO)
    @ColumnInfo(name = COLUMN_VIDEO)
    var video: String? = null

    @SerializedName(COLUMN_VOTE_AVERAGE)
    @ColumnInfo(name = COLUMN_VOTE_AVERAGE)
    var voteAverage: Double = 0.0

    @SerializedName(COLUMN_VOTE_COUNT)
    @ColumnInfo(name = COLUMN_VOTE_COUNT)
    var voteCount: Int = 0

    companion object {
        const val TABLE_NAME = "movie"
        const val COLUMN_ID = "id"
        const val COLUMN_ADULT = "adult"
        const val COLUMN_BACKDROP_PATH = "backdrop_path"
        const val COLUMN_ORIGINAL_LANGUAGE = "original_language"
        const val COLUMN_ORIGINAL_TITLE = "original_title"
        const val COLUMN_OVERVIEW = "overview"
        const val COLUMN_POPULARITY = "popularity"
        const val COLUMN_POSTER_PATH = "poster_path"
        const val COLUMN_RELEASE_DATE = "release_date"
        const val COLUMN_TITLE = "title"
        const val COLUMN_VIDEO = "video"
        const val COLUMN_VOTE_AVERAGE = "vote_average"
        const val COLUMN_VOTE_COUNT = "vote_count"
    }
}