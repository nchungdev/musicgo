package com.nchungdev.musicgo.ui.library

import androidx.annotation.IntDef

@IntDef(ViewType.SONG, ViewType.HEADER)
@Retention(AnnotationRetention.SOURCE)
annotation class ViewType {

    companion object {
        const val HEADER = 0
        const val SONG = 1
    }
}