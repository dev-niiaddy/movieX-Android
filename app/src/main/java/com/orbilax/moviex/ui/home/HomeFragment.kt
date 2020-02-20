package com.orbilax.moviex.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.orbilax.moviex.R
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.titled_section.view.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this, HomeViewModelFactory()).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initGenreRadioButtons()
        initNowPlaying(view)
        initComingSoon(view)
        initExplore(view)
    }

    private fun initGenreRadioButtons() {

    }

    private fun initNowPlaying(view: View) {
        val sectionsLayout = view.sectionsLayout
        val childView = LayoutInflater.from(context)
            .inflate(R.layout.titled_section, sectionsLayout, false)

        childView.titleTextView.text = getString(R.string.now_showing)
        childView.viewAllBtn.setOnClickListener {
            //
        }

        homeViewModel.nowPlayingResult.observe(this.viewLifecycleOwner,
            Observer { result ->
                result ?: return@Observer

                result.success?.apply {
                    childView.loadingLayout.visibility = View.GONE
                    this.movieSummaries?.apply {
                        val movies = this

                        Log.i("MovieX/Data", movies.size.toString())
                        with(childView.recyclerView) {
                            visibility = View.VISIBLE
                            layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            adapter = NowPlayingAdapter(movies)
                        }
                    }
                }

                result.apiError?.apply {
                    childView.loadingStatusText.text = result.apiError?.error
                }
            }
        )

        sectionsLayout.addView(childView)
    }

    private fun initComingSoon(view: View) {
        val sectionsLayout = view.sectionsLayout
        val childView = LayoutInflater.from(context)
            .inflate(R.layout.titled_section_small, sectionsLayout, false)

        childView.titleTextView.text = getString(R.string.coming_soon)

        homeViewModel.upcomingResult.observe(this.viewLifecycleOwner,
            Observer { result ->
                result ?: return@Observer

                result.success?.apply {
                    childView.loadingLayout.visibility = View.GONE
                    this.movieSummaries?.apply {
                        val movies = this.shuffled()

                        Log.i("MovieX/Data", movies.size.toString())
                        with(childView.recyclerView) {
                            visibility = View.VISIBLE
                            layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            adapter = MovieAdapter(movies)
                        }
                    }
                }

                result.apiError?.apply {
                    childView.loadingStatusText.text = result.apiError?.error
                }
            }
        )

        sectionsLayout.addView(childView)
    }

    private fun initExplore(view: View) {
        val sectionsLayout = view.sectionsLayout
        val childView = LayoutInflater.from(context)
            .inflate(R.layout.titled_section_small, sectionsLayout, false)

        childView.titleTextView.text = getString(R.string.explore)

        homeViewModel.exploreResult.observe(this.viewLifecycleOwner,
            Observer { result ->
                result ?: return@Observer

                result.success?.apply {
                    childView.loadingLayout.visibility = View.GONE
                    this.movieSummaries?.apply {
                        val movies = this

                        Log.i("MovieX/Data", movies.size.toString())
                        with(childView.recyclerView) {
                            visibility = View.VISIBLE
                            layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            adapter = MovieAdapter(movies)
                        }
                    }
                }

                result.apiError?.apply {
                    childView.loadingStatusText.text = result.apiError?.error
                }
            }
        )

        sectionsLayout.addView(childView)
    }

}