package com.nbs.decorado.customview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.nbs.nucleox.R

class CustomPrefixEditText : AppCompatEditText {

    private var mOriginalLeftPadding = -1f

    private var tagTextColor: Int? = null

    private var attrs: AttributeSet? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.attrs = attrs
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.attrs = attrs
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        parseAttrs(attrs)
    }

    private fun parseAttrs(attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context
                .obtainStyledAttributes(attrs, R.styleable.CustomPrefixEditText)
            try {
                tagTextColor = typedArray.getColor(R.styleable.CustomPrefixEditText_tag_text_color, 0)
            } finally {
                typedArray.recycle()
            }
        }
    }

    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        calculatePrefix()
    }

    private fun calculatePrefix() {
        if (mOriginalLeftPadding == -1f) {
            val prefix = tag as String
            val widths = FloatArray(prefix.length)
            paint.getTextWidths(prefix, widths)
            var textWidth = 0f
            for (w in widths) {
                textWidth += w
            }
            mOriginalLeftPadding = compoundPaddingLeft.toFloat()
            setPadding(
                (textWidth + mOriginalLeftPadding).toInt(),
                paddingRight, paddingTop,
                paddingBottom
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val prefix = tag as String
        val customPaint = paint
        tagTextColor?.let {
            if (it != 0)
                customPaint.color = it
        }
        canvas.drawText(
            prefix, mOriginalLeftPadding,
            getLineBounds(0, null).toFloat(), customPaint
        )
    }
}