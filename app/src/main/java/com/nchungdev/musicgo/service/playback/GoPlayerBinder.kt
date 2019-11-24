package com.nchungdev.musicgo.service.playback

import android.net.Uri

interface GoPlayerBinder {

    fun setDataSource(path: Uri)

    fun setNextDataSource(path: Uri?)

    fun start()

    fun stop()

    fun pause()

    fun resume()

    fun release()

    fun isPlaying(): Boolean

    fun duration(): Int

    fun position(): Int

    fun seek(msec: Int): Int

    fun setAudioSessionId(sessionId: Int)

    fun getAudioSessionId(): Int

    fun isInitialized(): Boolean

    fun setVolume(volume: Volume)

    fun setCallback(callback: Callback)

    interface Callback {
        fun onTrackEnded()
        fun onTrackWentToNext()
    }

    data class Volume(val left: Float, val right: Float)
}
