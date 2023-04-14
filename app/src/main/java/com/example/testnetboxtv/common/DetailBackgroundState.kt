package com.example.testnetboxtv.common

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.app.DetailsSupportFragmentBackgroundController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class DetailBackgroundState(private val fragment: DetailsSupportFragment) {

    private val detailsBackground by lazy { DetailsSupportFragmentBackgroundController(fragment) }
    private val backgroundManager by lazy {
        BackgroundManager.getInstance(fragment.requireActivity()).apply {
            attach(fragment.requireActivity().window)
            isAutoReleaseOnStop = false
        }
    }
    private val target = object : CustomTarget<Bitmap>() {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            detailsBackground.coverBitmap = resource
            detailsBackground.enableParallax()
        }

        override fun onLoadCleared(placeholder: Drawable?) {
        }
    }

    fun loadUrl(url: String) {
        Glide.with(fragment.requireContext())
            .asBitmap()
            .centerCrop()
            .load(url)
            .into<CustomTarget<Bitmap>>(target)
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