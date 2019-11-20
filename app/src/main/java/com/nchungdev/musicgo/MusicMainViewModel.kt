package com.nchungdev.musicgo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.nchungdev.musicgo.music_player.PlayerService
import com.nchungdev.musicgo.music_player.PlayerServiceConnection
import com.nchungdev.musicgo.music_player.PlayerState
import com.nchungdev.musicgo.repository.DistinctLiveData
import com.nchungdev.musicgo.repository.Song

class MusicMainViewModel(application: Application) : AndroidViewModel(application) {
    private val _musicPlayerState = DistinctLiveData<PlayerState>().apply {
        value = PlayerState.STOP
    }

    val playerState: DistinctLiveData<PlayerState> = _musicPlayerState
    val currentSong = DistinctLiveData<Song>()
    val currentPosInPlaylist = DistinctLiveData<Int>()

    private val connection = PlayerServiceConnection()

    private var currentProgress = 0

    fun playSong(song: Song) {
        if (connection.isRunning) {
            val playerBinder = connection.serviceBinder ?: return
            playerBinder.play(song)
            playerState.value = PlayerState.PLAY
            return
        }
        PlayerService.start(getApplication(), connection, song)
        playerState.value = PlayerState.PLAY
    }

    fun togglePlay() {
        val playerBinder = connection.serviceBinder ?: return
        when {
            playerBinder.isPlaying() -> {
                playerState.value = PlayerState.PAUSE
                pause()
            }
            playerBinder.isPause() -> {
                playerState.value = PlayerState.RESUME
                resume()
            }
            playerBinder.isStop() -> {
                playerState.value = PlayerState.PLAY
                play()
            }
        }
    }

    private fun resume() {
        val playerBinder = connection.serviceBinder ?: return
        playerBinder.seekTo(currentProgress)
    }

    private fun pause() {
        val playerBinder = connection.serviceBinder ?: return
        currentProgress = playerBinder.getCurrentProgress()
        playerBinder.pause()
    }

    private fun play() {
        val playerBinder = connection.serviceBinder ?: return
        val song = currentSong.value ?: return
        playerBinder.play(song)
    }

    fun nextSong() {
        val currentPos = currentPosInPlaylist.value ?: 0
        currentPosInPlaylist.value = currentPos + 1
    }

    fun stop() {
        val playerBinder = connection.serviceBinder ?: return
        playerBinder.stop()
    }
}
