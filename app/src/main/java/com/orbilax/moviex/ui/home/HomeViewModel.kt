package com.orbilax.moviex.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.orbilax.moviex.model.Genre
import com.orbilax.moviex.model.GenreList
import com.orbilax.moviex.repository.MoviesRepository
import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm

class HomeViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    private val _genreListResult: MutableLiveData<GenreListResult> = MutableLiveData()
    val genreListResult: LiveData<GenreListResult> = _genreListResult

    private val _nowPlayingResult: MutableLiveData<MoviePageResult> = MutableLiveData()
    val nowPlayingResult: LiveData<MoviePageResult> = _nowPlayingResult

    private val _upcomingResult: MutableLiveData<MoviePageResult> = MutableLiveData()
    val upcomingResult: LiveData<MoviePageResult> = _upcomingResult

    private val _exploreResult: MutableLiveData<MoviePageResult> = MutableLiveData()
    val exploreResult: LiveData<MoviePageResult> = _exploreResult

    private val disposables: CompositeDisposable = CompositeDisposable()

    init {
        getGenreList()
        getNowPlaying(1)
        getUpcoming(1)
        getExplore(2)
    }

    fun getGenreList() {
        val realm = Realm.getDefaultInstance()
        val list = realm.where(Genre::class.java).findAll().toMutableList()

        if (list.isNotEmpty()) {
            val data = GenreList()
            data.genresList = list
            _genreListResult.value = GenreListResult(data)
            return
        }


        val s = moviesRepository
            .getGenreList(
                {
                    _genreListResult.value = GenreListResult(it.data)
                    it.data.genresList?.apply {
                        realm.beginTransaction()
                        realm.copyToRealmOrUpdate(this.toMutableList())
                        realm.commitTransaction()
                    }
                },
                {
                    _genreListResult.value = GenreListResult(apiError = it.apiError)
                }
            )

        disposables.add(s)
    }

    fun getNowPlaying(page: Int) {
        val s = moviesRepository
            .getNowPlayingPage(
                page,
                {
                    _nowPlayingResult.value = MoviePageResult(it.data)
                },
                {
                    _nowPlayingResult.value = MoviePageResult(apiError = it.apiError)
                }
            )

        disposables.add(s)
    }

    fun getUpcoming(page: Int) {
        val s = moviesRepository
            .getUpcomingMovies(
                page,
                {
                    _upcomingResult.value = MoviePageResult(it.data)
                },
                {
                    _upcomingResult.value = MoviePageResult(apiError = it.apiError)
                }
            )

        disposables.add(s)
    }

    fun getExplore(page: Int) {
        val s = moviesRepository
            .getExplorePage(
                page,
                {
                    _exploreResult.value = MoviePageResult(it.data)
                },
                {
                    _exploreResult.value = MoviePageResult(apiError = it.apiError)
                }
            )

        disposables.add(s)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}