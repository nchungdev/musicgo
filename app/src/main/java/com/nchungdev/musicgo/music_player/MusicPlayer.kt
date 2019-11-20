package com.nchungdev.musicgo.music_player

import android.media.MediaPlayer

class MusicPlayer(controller: PlayerController) : MediaPlayer() {

    init {
        setOnBufferingUpdateListener(controller)
        setOnErrorListener(controller)
        setOnPreparedListener(controller)
        setOnCompletionListener(controller)
        setOnSeekCompleteListener(controller)
    }

}