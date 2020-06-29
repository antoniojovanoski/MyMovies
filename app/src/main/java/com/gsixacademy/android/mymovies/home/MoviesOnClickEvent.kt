package com.gsixacademy.android.mymovies.home

import com.gsixacademy.android.mymovies.models.MovieResult

sealed class MoviesOnClickEvent {
    data class onMovieClicked(val movie: MovieResult) : MoviesOnClickEvent()
}