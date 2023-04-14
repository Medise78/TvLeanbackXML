package com.example.testnetboxtv.presenter.ui.detail_screen

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.leanback.widget.*
import com.example.testnetboxtv.R
import com.example.testnetboxtv.domain.model.movie_detail.MovieDetailDto

class FullWidthDetailsMovieRowPresenter(private val fragment: Fragment) :
    FullWidthDetailsOverviewRowPresenter(DescriptionDetailPresenter()) {

    private enum class Options(@StringRes val stringRes: Int) {
        WATCH_TRAILER(R.string.watch_trailer),
        FAVORITE(R.string.favorite)
    }

    init {
        val sharedElementHelper = FullWidthDetailsOverviewSharedElementHelper()
        sharedElementHelper.setSharedElementEnterTransition(
            fragment.requireActivity(), "extra:Hero"
        )
        setListener(sharedElementHelper)
        isParticipatingEntranceTransition = true
    }

    fun buildActions(): ArrayObjectAdapter {
        setOnActionClickedListener { action ->
            val option = Options.values().first { it.ordinal == action.id.toInt() }
            Toast.makeText(fragment.requireContext(), option.stringRes, Toast.LENGTH_SHORT).show()
        }
        return ArrayObjectAdapter().apply {
            Options.values().forEach { option ->
                add(Action(option.ordinal.toLong(), fragment.getString(option.stringRes)))
            }
        }
    }
}

private class DescriptionDetailPresenter : AbstractDetailsDescriptionPresenter() {
    override fun onBindDescription(vh: ViewHolder?, item: Any?) {
        val movie = item as MovieDetailDto
        with(vh!!) {
            title.text = movie.title
            subtitle.text = movie.plot
            body.text = movie.awards
        }
    }
}