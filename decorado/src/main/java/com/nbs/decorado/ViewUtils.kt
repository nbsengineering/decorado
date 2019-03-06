package com.nbs.decorado

import android.content.Context
import android.util.DisplayMetrics

internal fun pxToDp(px: Int, context : Context): Int {
    val displayMetrics = context.resources.displayMetrics
    return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
}