package com.nchungdev.musicgo.music_player

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.nchungdev.musicgo.R
import com.nchungdev.musicgo.repository.Song

class MiniPlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private var textTitle: TextView
    private var imageCover: ImageView
    private var btnPlayOrPause: ImageButton
    private var btnSkipNext: ImageButton

    init {
        View.inflate(context, R.layout.layout_mini_player, this)
        textTitle = findViewById(R.id.textTitle)
        imageCover = findViewById(R.id.imageCover)
        btnPlayOrPause = findViewById(R.id.btnPauseOrPlay)
        btnSkipNext = findViewById(R.id.btnNext)
    }

    fun setSong(song: Song) {
        textTitle.text = song.title
        imageCover.setImageURI(song.cover)
    }

    fun pause() {
        btnPlayOrPause.setImageResource(R.drawable.ic_play_arrow_24dp)
    }

    fun playOrResume() {
        btnPlayOrPause.setImageResource(R.drawable.ic_pause_24dp)
    }

    fun setPlayOrPauseOnClickListener(onClick: () -> Unit) {
        btnPlayOrPause.setOnClickListener { onClick() }
    }
}
