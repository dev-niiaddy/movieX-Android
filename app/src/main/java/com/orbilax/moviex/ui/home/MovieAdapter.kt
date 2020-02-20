package com.orbilax.moviex.ui.home

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.orbilax.moviex.activity.details.DetailsActivity
import com.orbilax.moviex.R
import com.orbilax.moviex.model.MovieSummary
import com.orbilax.moviex.services.MovieService
import com.orbilax.moviex.util.inflate
import com.orbilax.moviex.util.toPercentage
import kotlinx.android.synthetic.main.now_playing_card.view.*

class MovieAdapter(private val movieSummaries: List<MovieSummary>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val v = parent.inflate(R.layout.movie_card)
        return MovieViewHolder(v)
    }

    override fun getItemCount(): Int = movieSummaries.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindMovie(movieSummaries[position])
    }

    class MovieViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var movieSummary: MovieSummary

        init {
            view.setOnClickListener {
                val i = Intent(it.context, DetailsActivity::class.java)
                i.putExtra(DetailsActivity.MOVIE_ID_KEY, movieSummary.id)
                it.context.startActivity(i)
            }
        }

        fun bindMovie(movieSummary: MovieSummary) {
            this.movieSummary = movieSummary

            Glide.with(view)
                .load("${MovieService.IMAGE_BASE_URL}w185/${movieSummary.posterPath}")
                .into(view.moviePoster)

            view.movieTitle.text = movieSummary.title
            view.languageTextView.text = movieSummary.originalLanguage?.capitalize()
            val rating = movieSummary.voteAverage.toPercentage()
            view.ratingPercentageTextView.text =
                view.context.getString(R.string.percentage_template, rating)
        }
    }
}