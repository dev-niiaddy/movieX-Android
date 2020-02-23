package com.orbilax.moviex.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.orbilax.moviex.R
import kotlinx.android.synthetic.main.fragment_favorites.*


class FavoriteFragment : Fragment() {

    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoriteViewModel =
            ViewModelProvider(this).get(FavoriteViewModel::class.java)
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        favoriteViewModel.favoriteMovies.observe(this.viewLifecycleOwner,
            Observer { result ->
                result ?: return@Observer

                if (result.isNotEmpty()) {
                    recyclerView.visibility = View.VISIBLE
                    emptyState.visibility = View.GONE
                } else {
                    return@Observer
                }

                with(recyclerView) {
                    //                    val displayMetrics = DisplayMetrics()
//                    activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
//                    val width = displayMetrics.widthPixels
                    layoutManager =
                        GridLayoutManager(context, 3)
                    adapter = FavoriteAdapter(result)
                }
            })
    }
}