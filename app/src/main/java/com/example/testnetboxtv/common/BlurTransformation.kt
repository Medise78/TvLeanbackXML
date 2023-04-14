package com.example.testnetboxtv.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

class BlurTransformation(private val context: Context) : BitmapTransformation() {

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val width = toTransform.width
        val height = toTransform.height

        // Create a canvas with the original image bitmap
        val blurredBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(blurredBitmap)
        canvas.drawBitmap(toTransform, 0f, 0f, null)

        // Apply blur effect
        val blurRadius = 25f // Adjust blur radius as needed
        val blurPaint = Paint()
        blurPaint.maskFilter = android.graphics.BlurMaskFilter(blurRadius, android.graphics.BlurMaskFilter.Blur.NORMAL)
        canvas.drawBitmap(blurredBitmap, 0f, 0f, blurPaint)

        return blurredBitmap
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        // Implement to uniquely identify the transformation for caching
    }
}
