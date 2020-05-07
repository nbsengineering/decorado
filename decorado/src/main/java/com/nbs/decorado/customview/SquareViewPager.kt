package com.nbs.decorado.customview

import android.content.Context
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet

class SquareViewPager : ViewPager {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = measuredWidth
        val height = measuredHeight

        if (width != height) {
            setMeasuredDimension(width, width)
        }
    }
}