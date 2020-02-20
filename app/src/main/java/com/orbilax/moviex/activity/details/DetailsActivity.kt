package com.orbilax.moviex.activity.details

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.orbilax.moviex.R
import com.orbilax.moviex.model.Favorite
import com.orbilax.moviex.services.MovieService
import com.orbilax.moviex.ui.home.MovieAdapter
import com.orbilax.moviex.util.toPercentage
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    private var movieId: Int =
        NO_EXTRA_FOUND

    private lateinit var detailsViewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        movieId = intent.getIntExtra(
            MOVIE_ID_KEY,
            NO_EXTRA_FOUND
        )

        if (movieId == NO_EXTRA_FOUND) {
            finish()
        }

        detailsViewModel = ViewModelProvider(this, DetailsViewModelFactory())
            .get(DetailsViewModel::class.java)

        detailsViewModel.getMovieDetails(movieId)
        detailsViewModel.getRecommendedMovies(movieId)
        observeViewModel()
    }

    private fun observeViewModel() {
        detailsViewModel.movieDetailsResult.observe(this,
            Observer { result ->
                result ?: return@Observer

                result.success?.apply {
                    progressLayout.visibility = View.GONE

                    Glide.with(this@DetailsActivity)
                        .load("${MovieService.IMAGE_BASE_URL}w780/${this.backdropPath}")
                        .into(movieBackDrop)

                    movieTitle.text = title
                    ratingPercentageTextView.text =
                        getString(R.string.percentage_template, voteAverage?.toPercentage())

                    languageText.text = "Language: ${originalLanguage?.capitalize()}"

                    genres?.apply {
                        var g = this.joinToString { it.name!! }

                        if (size > 3) {
                            g = slice(0..2).joinToString { it.name!! }
                        }

                        genresDurationText.text = g
                    }

                    synopsisText.text = overview

                    addToFavoriteBtn.setOnClickListener {
                        val favorite = Favorite()
                        favorite.id = id
                        favorite.title = title
                        favorite.posterPath = posterPath
                        favorite.voteAverage = voteAverage
                        favorite.language = originalLanguage

                        val realm = Realm.getDefaultInstance()
                        realm.beginTransaction()
                        realm.copyToRealmOrUpdate(favorite)
                        realm.commitTransaction()

                        Toast.makeText(
                            applicationContext,
                            R.string.added_to_favorites,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                result.apiError?.apply {}
            })


        detailsViewModel.recommendedMoviesResult.observe(this,
            Observer { result ->
                result ?: return@Observer

                result.success?.movieSummaries?.apply {
                    if (this.isEmpty()) {
                        recommendedMovies.visibility = View.GONE
                        recommendedMoviesRecyclerView.visibility = View.GONE
                    }
                    with(recommendedMoviesRecyclerView) {
                        layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        adapter = MovieAdapter(this@apply)
                    }
                }

                result.apiError?.apply {}
            })
    }

    companion object {
        const val MOVIE_ID_KEY = "moviex.tmdb.movie.id"
        private const val NO_EXTRA_FOUND = -1
    }
}