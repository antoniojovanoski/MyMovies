package com.gsixacademy.android.mymovies.api

import com.gsixacademy.android.mymovies.models.MovieListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val apiKey = "api_key=fc2bd4cdcd3ed20ae4c62633a8e5c5e8"

interface MovieDatabaseApi {
    @GET("movie/{category}?" + apiKey)
    fun getMovies(@Path("category") category: String?, @Query("page") page: Int): Call<MovieListResponse>
}