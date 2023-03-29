package com.example.myapplication.data

import com.example.myapplication.data.Dates
import com.example.myapplication.data.Movie

data class MovieResp(val dates: Dates,
                     val page: Long,
                     val results: List<Movie>,
                     val totalPages: Long,
                     val totalResults: Long)
