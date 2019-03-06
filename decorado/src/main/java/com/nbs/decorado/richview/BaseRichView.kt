package com.nbs.decorado.richview

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout

abstract class BaseRichView : FrameLayout {

    constructor(context: Context) : super(context) {
        this.initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.initView(context, attrs)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        this.initView(context, attrs)
    }

    protected open fun initView(context: Context, attrs: AttributeSet?) {
        val view: View = LayoutInflater.from(context).inflate(getLayout(), this, true)
        bindView(view)
    }

    abstract fun getLayout(): Int

    abstract fun bindView(view: View)


}