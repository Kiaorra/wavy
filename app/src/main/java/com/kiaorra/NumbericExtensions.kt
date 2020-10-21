package com.kiaorra

import android.content.res.Resources

val Int.toPx: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.toDp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Float.toPx: Float
    get() = this / Resources.getSystem().displayMetrics.density

val Float.toDp: Float
    get() = this * Resources.getSystem().displayMetrics.density