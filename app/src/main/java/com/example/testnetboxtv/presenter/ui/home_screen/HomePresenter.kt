package com.example.testnetboxtv.presenter.ui.home_screen

import android.view.ViewGroup
import androidx.leanback.widget.BaseCardView
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import coil.load
import com.example.testnetboxtv.domain.model.genres_movies.GenresMovieData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomePresenter : Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val imageCardView = ImageCardView(parent?.context).apply {
            isFocusable = true
            isFocusableInTouchMode = true
            cardType = BaseCardView.CARD_TYPE_INFO_OVER
            mainImageView.apply {
                layoutParams = BaseCardView.LayoutParams(300, 400)
            }
        }
        return ViewHolder(imageCardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        val movie = item as? GenresMovieData
        (viewHolder?.view as ImageCardView).apply {
            titleText = movie?.title
            contentText = movie?.imdbRating
            mainImageView.load(movie?.poster)
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
        (viewHolder?.view as ImageCardView).apply {
            mainImage = null
        }
    }
}