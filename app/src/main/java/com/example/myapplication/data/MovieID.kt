package com.example.myapplication.data

import com.google.gson.annotations.SerializedName

data class MovieID(val adult: Boolean,
                   @SerializedName("backdrop_path")
                   val backdropPath: String,
                   val belongsToCollection: Any? = null,
                   val budget: Long,
                   val genres: List<Genre>,
                   val homepage: String,
                   val id: Long,
                   val imdbID: String,
                   val originalLanguage: String,
                   val originalTitle: String,
                   val overview: String,
                   val popularity: Double,
                   @SerializedName("poster_path")
                   val posterPath: String,
                   val productionCompanies: List<ProductionCompany>,
                   val productionCountries: List<ProductionCountry>,
                   val releaseDate: String,
                   val revenue: Long,
                   val runtime: Long,
                   val spokenLanguages: List<SpokenLanguage>,
                   val status: String,
                   val tagline: String,
                   val title: String,
                   val video: Boolean,
                   val voteAverage: Double,
                   val voteCount: Long)

data class Genre (
    val id: Long,
    val name: String
)

data class ProductionCompany (
    val id: Long,
    val logoPath: String,
    val name: String,
    val originCountry: String
)

data class ProductionCountry (
    val iso3166_1: String,
    val name: String
)

data class SpokenLanguage (
    val englishName: String,
    val iso639_1: String,
    val name: String
)