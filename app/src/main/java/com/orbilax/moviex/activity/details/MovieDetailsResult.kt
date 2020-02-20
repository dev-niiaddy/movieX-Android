package com.orbilax.moviex.activity.details

import com.orbilax.moviex.model.MovieDetails
import com.orbilax.moviex.util.ApiError

data class MovieDetailsResult(
    var success: MovieDetails? = null,
    var apiError: ApiError? = null
)