package com.example.testnetboxtv.common

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.leanback.widget.DetailsOverviewRow
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.testnetboxtv.R

fun DetailsOverviewRow.loadImageUrl(context: Context, url:String){
    Glide.with(context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.gradiant)
        .into<CustomTarget<Drawable>>(object : CustomTarget<Drawable>(){
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                imageDrawable = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {}

        })
}