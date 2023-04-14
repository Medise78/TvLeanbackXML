package com.example.testnetboxtv.common

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.leanback.widget.OnItemViewClickedListener
import androidx.leanback.widget.OnItemViewSelectedListener
import androidx.navigation.fragment.findNavController
import com.example.testnetboxtv.domain.model.genres_movies.GenresMovieData
import com.example.testnetboxtv.presenter.ui.home_screen.HomeFragmentDirections

interface Collection {

    val backgroundState: BackgroundState

    fun Context.itemViewSelectedListener() = OnItemViewSelectedListener { _, movie, _, _ ->
        (movie as? GenresMovieData)?.let {
            if (it.images.isEmpty()) {
                backgroundState.emptyBackGround()
            } else if (it.images[0].isNotEmpty()) {
                backgroundState.loadUrl(movie.images[0])
            } else if (it.images[1].isNotEmpty()) {
                backgroundState.loadUrl(movie.images[1])
            } else if (it.images[2].isNotEmpty()) {
                backgroundState.loadUrl(movie.images[2])
            }
        }
    }

    fun Context.itemVewClickedListener(fragment: Fragment) =
        OnItemViewClickedListener { _, movie, _, _ ->
            (movie as? GenresMovieData)?.let {
                fragment.findNavController().navigate(
                    HomeFragmentDirections.actionToDetailFragment(it.id?:1)
                )
            }
        }
}