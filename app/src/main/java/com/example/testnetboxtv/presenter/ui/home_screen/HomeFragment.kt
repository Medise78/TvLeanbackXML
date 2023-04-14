package com.example.testnetboxtv.presenter.ui.home_screen

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.paging.PagingDataAdapter
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.lifecycle.lifecycleScope
import androidx.paging.filter
import com.example.testnetboxtv.R
import com.example.testnetboxtv.common.BackgroundState
import com.example.testnetboxtv.common.Resource
import com.example.testnetboxtv.domain.model.genres_movies.GenresMovieData
import com.example.testnetboxtv.paging.getVideoPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BrowseSupportFragment(), com.example.testnetboxtv.common.Collection {

    private lateinit var mSelectedVideo: GenresMovieData
    private lateinit var mRowsAdapter: ArrayObjectAdapter

    val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "NetBox"
        headersState = HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true

        fetchData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        onItemViewSelectedListener = requireContext().itemViewSelectedListener()
        onItemViewClickedListener = requireContext().itemVewClickedListener(this)
    }

    private fun fetchData() {
        mRowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        lifecycleScope.launch {
            viewModel.genreMoviesMap.collectLatest { resource ->
                mRowsAdapter.clear()
                when (resource) {
                    is Resource.Success -> {
                        progressBarManager.hide()
                        startEntranceTransition()
                        resource.data?.forEach { (genre, moviesFlow) ->
                            val moviesAdapter = getVideoPagingAdapter(HomePresenter())
                            moviesFlow.onEach { pagingData ->
                                moviesAdapter.submitData(pagingData)
                            }.launchIn(lifecycleScope)
                            val headerItem = HeaderItem(genre.name)
                            val listRow = ListRow(headerItem, moviesAdapter)
                            mRowsAdapter.add(listRow)

                        }
                    }
                    is Resource.Error -> {
                        progressBarManager.hide()
                    }
                    is Resource.Loading -> {
                        progressBarManager.show()
                        prepareEntranceTransition()
                    }
                }
            }
        }


        viewModel.fetchGenresMoviesMap()
        adapter = mRowsAdapter
    }

    override val backgroundState: BackgroundState = BackgroundState(this)
}
