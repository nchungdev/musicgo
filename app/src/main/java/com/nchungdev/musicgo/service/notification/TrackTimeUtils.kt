package com.nchungdev.musicgo.service.notification

import android.text.format.DateUtils

object TrackTimeUtils {

    fun format(millis: Int): String {
        val seconds = millis / 1000
        return when {
            seconds < 10 -> "00:0$seconds"
            seconds < 60 -> "00:$seconds"
            else -> DateUtils.formatElapsedTime(seconds.toLong())
        }
    }
}