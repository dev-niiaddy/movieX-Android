package com.orbilax.moviex.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

class BelongsToCollection {

    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null
    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null
}
