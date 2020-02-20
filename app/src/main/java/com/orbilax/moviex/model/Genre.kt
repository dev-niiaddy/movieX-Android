package com.orbilax.moviex.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class Genre: RealmObject() {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("name")
    @Expose
    var name: String? = null
}
