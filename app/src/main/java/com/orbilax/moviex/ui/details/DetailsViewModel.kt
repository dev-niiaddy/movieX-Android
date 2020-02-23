package com.orbilax.moviex.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.orbilax.moviex.repository.MoviesRepository
import com.orbilax.moviex.ui.home.MoviePageResult
import io.reactivex.disposables.CompositeDisposable

class DetailsViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    private val _movieDetailsResult: MutableLiveData<MovieDetailsResult> = MutableLiveData()
    val movieDetailsResult: LiveData<MovieDetailsResult> = _movieDetailsResult

    private val _recommendedMoviesResult: MutableLiveData<MoviePageResult> = MutableLiveData()
    val recommendedMoviesResult: LiveData<MoviePageResult> = _recommendedMoviesResult

    private val disposables: CompositeDisposable = CompositeDisposable()

    fun getMovieDetails(movieId: Int) {
        val s = moviesRepository.getMovieDetails(
            movieId,
            {
                _movieDetailsResult.value =
                    MovieDetailsResult(it.data)
            },
            {
                _movieDetailsResult.value =
                    MovieDetailsResult(apiError = it.apiError)
            }
        )

        disposables.add(s)
    }

    fun getRecommendedMovies(movieId: Int) {
        val s = moviesRepository.getRecommendedMovies(
            movieId,
            {
                _recommendedMoviesResult.value = MoviePageResult(it.data)
            },
            {
                _recommendedMoviesResult.value = MoviePageResult(apiError = it.apiError)
            }
        )

        disposables.add(s)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}