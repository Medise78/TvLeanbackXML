package com.example.testnetboxtv.presenter.ui.detail_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.DetailsOverviewRow
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.testnetboxtv.R
import com.example.testnetboxtv.common.DetailBackgroundState
import com.example.testnetboxtv.common.Resource
import com.example.testnetboxtv.common.loadImageUrl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : DetailsSupportFragment() {

    private val detailsBackgroundState = DetailBackgroundState(this)

    private val detailViewModel: DetailViewModel by viewModels()

    private val args:DetailFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        args.id
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val presenter = FullWidthDetailsMovieRowPresenter(this)
        val rowAdapter = ArrayObjectAdapter(presenter)
        adapter = rowAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                detailViewModel.detailState.collect { movie ->
                    when (movie) {
                        is Resource.Error -> {
                            progressBarManager.hide()
                        }
                        is Resource.Success -> {
                            progressBarManager.hide()
                            val row = DetailsOverviewRow(movie.data)
                            row.loadImageUrl(
                                requireContext(),
                                movie.data?.poster!!
                            )
                            row.actionsAdapter = presenter.buildActions()
                            rowAdapter.add(0, row)

                            if (movie.data.images.isEmpty()){
                                detailsBackgroundState.emptyBackGround()
                            }else{
                                detailsBackgroundState.loadUrl(movie.data.images[0])
                            }
                        }
                        is Resource.Loading -> {
                            progressBarManager.show()
                        }
                    }
                }
            }
        }
    }
}