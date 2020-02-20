package com.orbilax.moviex.ui.favorites

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.orbilax.moviex.R
import com.orbilax.moviex.util.AutoFitGridLayoutManager
import kotlinx.android.synthetic.main.fragment_dashboard.*


class FavoriteFragment : Fragment() {

    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoriteViewModel =
            ViewModelProvider(this).get(FavoriteViewModel::class.java)
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        favoriteViewModel.favoriteMovies.observe(this.viewLifecycleOwner,
            Observer { result ->
                result ?: return@Observer

                with(recyclerView) {
//                    val displayMetrics = DisplayMetrics()
//                    activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
//                    val width = displayMetrics.widthPixels
                    layoutManager =
                        GridLayoutManager(context, 2)
                    adapter = FavoriteAdapter(result)
                }
            })
    }
}