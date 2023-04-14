package com.example.testnetboxtv.paging

import androidx.leanback.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.testnetboxtv.domain.model.genres_movies.GenresMovieData
import com.example.testnetboxtv.presenter.ui.home_screen.HomePresenter

fun getVideoPagingAdapter(homePresenter: HomePresenter): PagingDataAdapter<GenresMovieData> =
    PagingDataAdapter(homePresenter,
        object : DiffUtil.ItemCallback<GenresMovieData>() {
            override fun areItemsTheSame(
                oldItem: GenresMovieData,
                newItem: GenresMovieData
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: GenresMovieData,
                newItem: GenresMovieData
            ): Boolean {
                return oldItem == newItem
            }

        })

