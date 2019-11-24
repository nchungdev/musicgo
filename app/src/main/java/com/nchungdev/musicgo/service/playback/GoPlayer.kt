package com.nchungdev.musicgo.service.playback

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.audiofx.AudioEffect
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.util.Log

class GoPlayer(private val context: Context) : GoPlayerBinder,
    MediaPlayer.OnErrorListener,
    MediaPlayer.OnCompletionListener,
    MediaPlayer.OnPreparedListener,
    MediaPlayer.OnSeekCompleteListener {

    private var currentPlayer = MediaPlayer()
    private var nextPlayer: MediaPlayer? = null
    private var callback: GoPlayerBinder.Callback? = null
    private var volume = GoPlayerBinder.Volume(1f, 1f)
    private var isInitialized = false

    init {
        currentPlayer.setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK)
    }

    override fun setDataSource(path: Uri) {
        isInitialized = false
        isInitialized = setDataSourceImpl(currentPlayer, path)
        if (isInitialized) {
            setNextDataSource(null)
        }
    }

    private fun setDataSourceImpl(player: MediaPlayer, path: Uri): Boolean {
        try {
            player.reset()
            player.setDataSource(context, path)
            player.prepare()
        } catch (e: Exception) {
            throw e
        }
        player.setOnSeekCompleteListener(this)
        player.setOnPreparedListener(this)
        player.setOnCompletionListener(this)
        player.setOnErrorListener(this)
        val intent = Intent(AudioEffect.ACTION_OPEN_AUDIO_EFFECT_CONTROL_SESSION).apply {
            putExtra(AudioEffect.EXTRA_AUDIO_SESSION, getAudioSessionId())
            putExtra(AudioEffect.EXTRA_PACKAGE_NAME, context.packageName)
            putExtra(AudioEffect.EXTRA_CONTENT_TYPE, AudioEffect.CONTENT_TYPE_MUSIC)
        }
        context.sendBroadcast(intent)
        return true
    }

    override fun setNextDataSource(path: Uri?) {
        path ?: return
        nextPlayer = MediaPlayer()
        val nextPlayer = nextPlayer ?: return
        nextPlayer.setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK)
        nextPlayer.audioSessionId = getAudioSessionId()
        if (setDataSourceImpl(nextPlayer, path)) {
            try {
                currentPlayer.setNextMediaPlayer(nextPlayer)
            } catch (e: java.lang.Exception) {
                this.nextPlayer?.release()
                this.nextPlayer = null
            }
        } else {
            this.nextPlayer?.release()
            this.nextPlayer = null
        }
    }

    override fun start() {
        currentPlayer.start()
    }

    override fun stop() {
        currentPlayer.stop()
        isInitialized = false
    }

    override fun pause() {
        currentPlayer.pause()
    }

    override fun setCallback(callback: GoPlayerBinder.Callback) {
        this.callback = callback
    }

    override fun resume() {
        if (isInitialized) {
            seek(currentPlayer.currentPosition)
        }
    }

    override fun release() {
        stop()
        currentPlayer.release()
        val nextPlayer = nextPlayer ?: return
        nextPlayer.release()
    }

    override fun isPlaying() = isInitialized && currentPlayer.isPlaying

    override fun isInitialized() = isInitialized

    override fun duration(): Int = if (isInitialized) currentPlayer.duration else -1

    override fun position(): Int = if (isInitialized) currentPlayer.currentPosition else -1

    override fun seek(msec: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            currentPlayer.seekTo(msec.toLong(), MediaPlayer.SEEK_NEXT_SYNC)
        } else {
            currentPlayer.seekTo(msec)
        }
        return msec
    }

    override fun setVolume(volume: GoPlayerBinder.Volume) {
        currentPlayer.setVolume(volume.left, volume.right)
        this.volume = volume
    }

    override fun getAudioSessionId() = currentPlayer.audioSessionId

    override fun setAudioSessionId(sessionId: Int) {
        currentPlayer.audioSessionId = sessionId
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {

        return false
    }

    override fun onCompletion(mp: MediaPlayer?) {
        if (currentPlayer == mp && nextPlayer != null) {
            isInitialized = false
            currentPlayer.release()
            currentPlayer = nextPlayer as MediaPlayer
            isInitialized = true
            nextPlayer = null
            val callback = callback ?: return
            callback.onTrackWentToNext()
        } else {
            val callback = callback ?: return
            callback.onTrackEnded()
        }
    }

    override fun onPrepared(mp: MediaPlayer?) {

    }

    override fun onSeekComplete(mp: MediaPlayer?) {

    }
}