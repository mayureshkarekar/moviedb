package com.moviedb.model

import com.google.gson.annotations.SerializedName

data class Review(@SerializedName("id") val id: String) {
    @SerializedName("author")
    var author: String? = null

    @SerializedName("content")
    var content: String? = null
}