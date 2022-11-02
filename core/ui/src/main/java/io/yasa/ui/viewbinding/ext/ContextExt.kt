package io.yasa.ui.viewbinding.ext

import android.content.Context

fun Context.isPortrait(): Boolean {
    return resources.getBoolean(io.yasa.ui.R.bool.is_portrait)
}

fun Context.isTablet(): Boolean {
    return resources.getBoolean(io.yasa.ui.R.bool.is_tablet)
}