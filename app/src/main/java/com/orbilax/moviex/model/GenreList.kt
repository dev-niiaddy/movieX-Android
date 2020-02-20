package com.orbilax.moviex.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class GenreList {
    @SerializedName("genres")
    @Expose
    var genresList: List<Genre>? = null
}