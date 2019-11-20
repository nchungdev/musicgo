package com.nchungdev.musicgo.music_player

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import com.nchungdev.musicgo.repository.Song

class PlayerServiceBinder(
    private val service: Service,
    private val musicPlayer: MusicPlayer,
    intent: Intent
) : Binder() {

    private var isStop = true

    init {
        val song = intent.getParcelableExtra<Song>("song")
        if (song != null) {
            play(song)
        }
    }

    fun play(song: Song) {
        musicPlayer.reset()
        musicPlayer.setDataSource(service.applicationContext, song.data)
        musicPlayer.prepare()
        isStop = false
    }

    fun pause() {
        musicPlayer.pause()
        isStop = false
    }

    fun seekTo(msec: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            musicPlayer.seekTo(msec.toLong(), MediaPlayer.SEEK_CLOSEST_SYNC)
        } else {
            musicPlayer.seekTo(msec)
        }
        isStop = false
    }

    fun stop() {
        isStop = true
        musicPlayer.stop()
        musicPlayer.release()
    }

    fun isPlaying() = musicPlayer.isPlaying

    fun isPause() = !isPlaying()

    fun isStop() = isStop

    fun getCurrentProgress() = musicPlayer.currentPosition
}