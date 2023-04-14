package com.example.testnetboxtv.common

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.fragment.app.Fragment
import androidx.leanback.app.BackgroundManager
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BackgroundState(private val fragment: Fragment) {
    private val backgroundManager by lazy {
        val activity = fragment.requireActivity()
        BackgroundManager.getInstance(activity).apply {
            isAutoReleaseOnStop = false
            attach(activity.window)
        }
    }

    private val target = object : CustomTarget<Bitmap>() {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            backgroundManager.setBitmap(resource)
        }

        override fun onLoadCleared(placeholder: Drawable?) {}
    }

    private var job: Job? = null
    fun loadUrl(url: String) {
        job?.cancel()
        job = fragment.lifecycleScope.launch {
            Glide.with(fragment.requireContext())
                .asBitmap()
                .load(url)
                .centerCrop()
                .into(target)
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