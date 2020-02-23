package com.orbilax.moviex.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.orbilax.moviex.ui.home.MovieAdapter

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun Double.toPercentage(): Int {
    return (this * 10).toInt()
}