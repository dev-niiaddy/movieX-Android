package com.orbilax.moviex.activity.view_all

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.orbilax.moviex.R

class ViewAllActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all)
    }

    companion object {
        const val NOW_PLAYING = "now.playing"
        const val COMING_SOON = "coming.soon"
        const val EXPLORE = "explore.discover"
    }
}