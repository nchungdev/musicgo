package com.nchungdev.musicgo.ui.library

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DivideItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
    }
}