package com.nchungdev.musicgo.custom

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.nchungdev.musicgo.R
import kotlinx.android.synthetic.main.layout_big_play_controller.view.*

class BigPlayControllerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    init {
        inflate(context, R.layout.layout_big_play_controller, this)
    }

    fun setControllerListener(listener: OnControllerListener) {
        btnLyric.setOnClickListener { listener.onOpenLyric() }
        btnPlayPause.setOnClickListener { listener.onTogglePause() }
        btnSkipNext.setOnClickListener { listener.onNext() }
        btnSkipPrevious.setOnClickListener { listener.onPrev() }
        btnPlaylist.setOnClickListener { listener.onOpenPlaylist() }
    }

    interface OnControllerListener {
        fun onNext()
        fun onPrev()
        fun onTogglePause()
        fun onOpenLyric()
        fun onOpenPlaylist()
    }
}
