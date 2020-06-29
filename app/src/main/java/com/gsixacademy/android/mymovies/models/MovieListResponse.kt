package com.gsixacademy.android.mymovies.models

import java.util.ArrayList

class MovieListResponse(
    val page: Int,
    val results: ArrayList<MovieResult>,
    val total_results: Int,
    val total_pages:Int
)