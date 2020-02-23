package com.orbilax.moviex.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.orbilax.moviex.R
import com.orbilax.moviex.model.Favorite
import com.orbilax.moviex.services.MovieService
import com.orbilax.moviex.ui.home.MovieAdapter
import com.orbilax.moviex.util.toPercentage
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()

    private lateinit var detailsViewModel: DetailsViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = args.movieId

        detailsViewModel = ViewModelProvider(
            this,
            DetailsViewModelFactory()
        ).get(DetailsViewModel::class.java)

        detailsViewModel.getMovieDetails(movieId)
        detailsViewModel.getRecommendedMovies(movieId)
        observeViewModel()
    }

    private fun observeViewModel() {
        detailsViewModel.movieDetailsResult.observe(viewLifecycleOwner,
            Observer { result ->
                result ?: return@Observer

                result.success?.apply {
                    progressLayout.visibility = View.GONE

                    Glide.with(activity!!)
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
                            activity,
                            R.string.added_to_favorites,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                result.apiError?.apply {}
            })


        detailsViewModel.recommendedMoviesResult.observe(viewLifecycleOwner,
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
}