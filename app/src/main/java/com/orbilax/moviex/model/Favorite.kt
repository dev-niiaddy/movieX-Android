package com.orbilax.moviex.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Favorite : RealmObject() {
    @PrimaryKey
    var id: Int? = null

    var title: String? = null

    var posterPath: String? = null

    var voteAverage: Double? = null

    var language: String? = null
}