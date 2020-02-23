package com.orbilax.moviex.ui.favorites

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.orbilax.moviex.R
import com.orbilax.moviex.model.Favorite
import com.orbilax.moviex.services.MovieService
import com.orbilax.moviex.util.inflate
import com.orbilax.moviex.util.toPercentage
import kotlinx.android.synthetic.main.now_playing_card.view.*

class FavoriteAdapter(private val movieSummaries: List<Favorite>) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder{
        val v = parent.inflate(R.layout.grid_movie_card)
        return FavoriteViewHolder(v)
    }

    override fun getItemCount(): Int = movieSummaries.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindMovie(movieSummaries[position])
    }

    class FavoriteViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var favorite: Favorite

        init {
            view.setOnClickListener {
                val navController: NavController =
                    (view.context as Activity).findNavController(R.id.mainNavHostFragment)
                navController.navigate(R.id.navigation_details)
            }
        }

        fun bindMovie(favorite: Favorite) {
            this.favorite = favorite

            Glide.with(view)
                .load("${MovieService.IMAGE_BASE_URL}w185/${favorite.posterPath}")
                .into(view.moviePoster)

            view.movieTitle.text = favorite.title
            view.languageTextView.text = favorite.language?.capitalize()
            val rating = favorite.voteAverage?.toPercentage()
            view.ratingPercentageTextView.text =
                view.context.getString(R.string.percentage_template, rating)
        }
    }
}