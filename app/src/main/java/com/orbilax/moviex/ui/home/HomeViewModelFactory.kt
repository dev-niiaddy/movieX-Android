package com.orbilax.moviex.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.orbilax.moviex.repository.MoviesRepository

class HomeViewModelFactory: ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                MoviesRepository()
            ) as T
        }
        throw IllegalArgumentException("View model class should be a subclass of HomeViewModel")
    }
}