package com.nchungdev.musicgo.music_player

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MusicPlayerService : Service(), MusicPlayerListener {

    private val musicPlayer = MusicPlayer(this)


    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {

        return false
    }

    override fun onPrepared(mp: MediaPlayer?) {

    }

    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {

    }

    override fun onBind(intent: Intent): IBinder? {

        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        musicPlayer.start()
        return super.onStartCommand(intent, flags, startId)
    }
}
