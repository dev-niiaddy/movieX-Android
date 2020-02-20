package com.orbilax.moviex.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.orbilax.moviex.model.Favorite
import io.realm.Realm

class FavoriteViewModel : ViewModel() {

    private val _favoriteMovies: MutableLiveData<List<Favorite>> = MutableLiveData()
    val favoriteMovies: LiveData<List<Favorite>> = _favoriteMovies

    init {
        val realm = Realm.getDefaultInstance()
        _favoriteMovies.value = realm.where(Favorite::class.java).findAll()
    }
}