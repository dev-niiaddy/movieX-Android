package com.orbilax.moviex.ui.home

import com.orbilax.moviex.model.MoviesPage
import com.orbilax.moviex.util.ApiError

data class MoviePageResult(
    var success: MoviesPage? = null,
    var apiError: ApiError? = null
)