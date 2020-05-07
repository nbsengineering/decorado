package com.nbs.decorado.customview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.nbs.decorado.R

class RatioImageView : AppCompatImageView {
    // Summary :
    // don't set this RationImageView with wrap_content because will only return 1 in measuredWidth and measureHeight
    // widthRatio > heightRatio will using your measuredWidth on height
    // heightRatio > widthRatio will using your measureHeight on width

    private var widthRatio = 1
    private var heightRatio = 1

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        parseAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        parseAttrs(attrs)
    }

    fun setRatio(widthRatio: Int, heightRatio: Int) {
        this.widthRatio = widthRatio
        this.heightRatio = heightRatio
    }

    private fun parseAttrs(attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView)

            try {
                typedArray.getInteger(R.styleable.RatioImageView_width_ratio, 1).let {
                    widthRatio = it
                }
                typedArray.getInteger(R.styleable.RatioImageView_height_ratio, 1).let {
                    heightRatio = it
                }
            } finally {
                typedArray.recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width: Int = measuredWidth
        var height: Int = measuredHeight
        when {
            widthRatio > heightRatio -> {
                val factor: Double = (heightRatio.toDouble() / widthRatio.toDouble())
                height = (measuredWidth.toDouble() * factor).toInt()
            }
            heightRatio > widthRatio -> {
                val factor: Double = (widthRatio.toDouble() / heightRatio.toDouble())
                width = (measuredHeight.toDouble() * factor).toInt()
            }
            widthRatio == heightRatio -> {
                width = measuredWidth
                height = measuredWidth
            }
        }
        setMeasuredDimension(width, height)
    }
}