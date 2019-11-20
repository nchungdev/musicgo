package com.nchungdev.musicgo.music_player

import android.media.MediaPlayer

class PlayerController : MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener,
    MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener,
    MediaPlayer.OnSeekCompleteListener {

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        return false
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp?.start()
    }

    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {

    }

    override fun onCompletion(mp: MediaPlayer?) {

    }

    override fun onSeekComplete(mp: MediaPlayer?) {
        mp?.start()
    }
}
