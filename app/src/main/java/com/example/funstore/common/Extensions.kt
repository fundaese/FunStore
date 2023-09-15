package com.example.funstore.common

import android.widget.ImageView
import com.bumptech.glide.Glide
import android.view.View

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context).load(url).into(this)
}

fun View.gone() {
    visibility= View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}