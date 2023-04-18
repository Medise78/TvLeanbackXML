package com.example.testnetboxtv.common

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.leanback.app.BackgroundManager
import androidx.lifecycle.lifecycleScope
import coil.Coil
import coil.ImageLoader
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class BackgroundState(private val fragment: Fragment) {
    private val backgroundManager by lazy {
        val activity = fragment.requireActivity()
        BackgroundManager.getInstance(activity).apply {
            isAutoReleaseOnStop = false
            attach(activity.window)
        }
    }

    private val target = object : coil.target.Target {
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

    private var job: Job? = null
    fun loadUrl(url: String) {
        val imageLoader = ImageLoader(fragment.requireContext())
        job?.cancel()
        job = fragment.lifecycleScope.launch {
            val request = ImageRequest.Builder(fragment.requireContext())
                .data(url)
                .target(target)
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
        backgroundManager.apply {
            setBitmap(bitmap)
            clearDrawable()
        }
    }
}