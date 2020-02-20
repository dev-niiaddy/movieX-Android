package com.orbilax.moviex.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class DateRange {

    @SerializedName("maximum")
    @Expose
    var maximum: String? = null
    @SerializedName("minimum")
    @Expose
    var minimum: String? = null

}