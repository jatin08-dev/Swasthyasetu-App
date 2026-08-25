package com.example.animation

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs
import kotlin.math.max

class ZoomPage:ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val scale= max(0.85f,1- abs(position))
        page.scaleX=scale
        page.scaleY=scale
        page.alpha=scale
    }
}