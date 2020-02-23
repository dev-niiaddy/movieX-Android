package com.orbilax.moviex.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.orbilax.moviex.repository.MoviesRepository

class DetailsViewModelFactory: ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(
                MoviesRepository()
            ) as T
        }
        throw IllegalArgumentException("View model class should be a subclass of HomeViewModel")
    }
}