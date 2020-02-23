package com.orbilax.moviex.activity.view_all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.orbilax.moviex.ui.details.MovieDetailsResult

class ViewAllViewModel: ViewModel() {

    private val _movieDetailsResult: MutableLiveData<MovieDetailsResult> = MutableLiveData()
    val movieDetailsResult: LiveData<MovieDetailsResult> = _movieDetailsResult
}