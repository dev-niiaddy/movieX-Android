package com.orbilax.moviex.ui.home

import com.orbilax.moviex.model.GenreList
import com.orbilax.moviex.util.ApiError

data class GenreListResult(
    var success: GenreList? = null,
    var apiError: ApiError? = null
)