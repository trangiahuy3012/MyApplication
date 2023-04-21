package com.example.myapplication.data

import com.google.gson.annotations.SerializedName

data class Movie(
    val adult: Boolean,
    val backdropPath: String,
    val genreIDS: List<Long>,
    val id: Long,
    val originalLanguage: OriginalLanguage,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,

    @SerializedName("poster_path")
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Long
)
