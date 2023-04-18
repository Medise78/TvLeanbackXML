package com.example.testnetboxtv.common

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.app.DetailsSupportFragmentBackgroundController
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailBackgroundState(private val fragment: DetailsSupportFragment) {

    private val detailsBackground by lazy { DetailsSupportFragmentBackgroundController(fragment) }
    private val backgroundManager by lazy {
        BackgroundManager.getInstance(fragment.requireActivity()).apply {
            attach(fragment.requireActivity().window)
            isAutoReleaseOnStop = false
        }
    }

    private val target = object : coil.target.Target{
        override fun onError(error: Drawable?) {
            super.onError(error)
            backgroundManager.drawable = error
        }

        override fun onStart(placeholder: Drawable?) {
            super.onStart(placeholder)
            backgroundManager.drawable = placeholder
        }

        override fun onSuccess(result: Drawable) {
            super.onSuccess(result)
            backgroundManager.drawable = result
        }
    }

    private var job:Job? = null

    fun loadUrl(url: String) {
        val imageLoader = ImageLoader(fragment.requireContext())
        job?.cancel()
        job = fragment.lifecycleScope.launch {
            val request = ImageRequest.Builder(fragment.requireContext())
                .data(url)
                .target(target)
                .crossfade(true)
                .build()
            imageLoader.enqueue(request)
        }
    }

    fun emptyBackGround() {
        val gradiant = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(Color.BLACK, Color.WHITE)
        )
        gradiant.setBounds(0, 0, 100, 100)
        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        gradiant.draw(canvas)
        backgroundManager.setBitmap(bitmap)
    }
}