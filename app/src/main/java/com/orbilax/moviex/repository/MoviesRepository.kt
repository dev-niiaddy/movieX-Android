package com.orbilax.moviex.repository

import com.orbilax.moviex.model.Genre
import com.orbilax.moviex.model.GenreList
import com.orbilax.moviex.model.MovieDetails
import com.orbilax.moviex.model.MoviesPage
import com.orbilax.moviex.services.MovieService
import com.orbilax.moviex.util.Result
import com.orbilax.moviex.util.handleApiError
import com.orbilax.moviex.util.handleSuccess
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm

class MoviesRepository {

    private val movieService: MovieService = MovieService.createService()

    fun getNowPlayingPage(
        page: Int,
        onSuccess: (Result.Success<MoviesPage>) -> Unit,
        onFailure: (Result.Error) -> Unit
    ): Disposable {
        return movieService
            .getNowPlayingPage(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                handleSuccess(it, onSuccess)
            }, {
                handleApiError(it, onFailure)
            })
    }

    fun getUpcomingMovies(
        page: Int,
        onSuccess: (Result.Success<MoviesPage>) -> Unit,
        onFailure: (Result.Error) -> Unit
    ): Disposable {
        return movieService
            .getUpcomingMovies(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                handleSuccess(it, onSuccess)
            }, {
                handleApiError(it, onFailure)
            })
    }

    fun getExplorePage(
        page: Int,
        onSuccess: (Result.Success<MoviesPage>) -> Unit,
        onFailure: (Result.Error) -> Unit
    ): Disposable {
        return movieService
            .getExplorePage(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                handleSuccess(it, onSuccess)
            }, {
                handleApiError(it, onFailure)
            })
    }

    fun getMovieDetails(
        movieId: Int,
        onSuccess: (Result.Success<MovieDetails>) -> Unit,
        onFailure: (Result.Error) -> Unit
    ): Disposable {
        return movieService
            .getMovieDetails(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                handleSuccess(it, onSuccess)
            }, {
                handleApiError(it, onFailure)
            })
    }

    fun getGenreList(
        onSuccess: (Result.Success<GenreList>) -> Unit,
        onFailure: (Result.Error) -> Unit
    ): Disposable {

        val realm = Realm.getDefaultInstance()
        val list = realm.where(Genre::class.java).findAll().toList()

        if (list.isNotEmpty()) {
            val g = GenreList()
            g.genresList = list
            return Observable.just(g)
                .subscribe({
                    handleSuccess(it, onSuccess)
                }, {
                    handleApiError(it, onFailure)
                })
        }


        return movieService
            .getGenreList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.genresList?.apply {
                    if (isEmpty()) return@apply
                    realm.beginTransaction()
                    realm.copyToRealmOrUpdate(this)
                    realm.commitTransaction()
                }
                handleSuccess(it, onSuccess)
            }, {
                handleApiError(it, onFailure)
            })
    }

    fun getRecommendedMovies(
        movieId: Int,
        onSuccess: (Result.Success<MoviesPage>) -> Unit,
        onFailure: (Result.Error) -> Unit
    ): Disposable {
        return movieService
            .getRecommendedMovies(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                handleSuccess(it, onSuccess)
            }, {
                handleApiError(it, onFailure)
            })
    }
}