package com.nchungdev.musicgo.music_player

import android.media.MediaPlayer

class MusicPlayer(listener: MusicPlayerListener) : MediaPlayer() {

    init {
        setOnBufferingUpdateListener(listener)
        setOnErrorListener(listener)
        setOnPreparedListener(listener)
    }

}