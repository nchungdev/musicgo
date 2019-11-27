package com.nchungdev.musicgo.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.nchungdev.musicgo.R
import com.nchungdev.musicgo.repository.Song
import com.nchungdev.musicgo.util.Uri2Bitmap

class PlayControllerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var textTitle: TextView
    private var imageCover: ImageView
    private var btnPlayOrPause: ImageButton
    private var btnSkipNext: ImageButton
    private var btnPrev: ImageButton

    init {
        View.inflate(context, R.layout.layout_play_controller, this)
        textTitle = findViewById(R.id.textTitle)
        imageCover = findViewById(R.id.imageCover)
        btnPlayOrPause = findViewById(R.id.btnPlayPause)
        btnSkipNext = findViewById(R.id.btnSkipNext)
        btnPrev = findViewById(R.id.btnPrev)
    }

    fun setSong(song: Song) {
        textTitle.text = song.title
        Uri2Bitmap(context, song.cover, R.drawable.ic_play_24dp) {
            imageCover.setImageBitmap(it)
        }
    }

    fun setPlayButtonIcon(@DrawableRes drawableId: Int) {
        btnPlayOrPause.setImageResource(drawableId)
    }

    fun setOnControllerListener(controller: OnPlayerController) {
        setOnClickListener { controller.onExpand() }
        btnPlayOrPause.setOnClickListener { controller.onTogglePause() }
        btnSkipNext.setOnClickListener { controller.onNext() }
        btnPrev.setOnClickListener { controller.onPrev() }
    }

    interface OnPlayerController {
        fun onExpand()
        fun onNext()
        fun onPrev()
        fun onTogglePause()
    }
}
