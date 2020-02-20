package com.orbilax.moviex.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class MoviesPage {
    @SerializedName("results")
    @Expose
    var movieSummaries: List<MovieSummary>? = null
    @SerializedName("page")
    @Expose
    var page: Int = 0
    @SerializedName("total_results")
    @Expose
    var totalResults: Int = 0
    @SerializedName("dates")
    @Expose
    var dateRange: DateRange? = null
    @SerializedName("total_pages")
    @Expose
    var totalPages: Int = 0
}
