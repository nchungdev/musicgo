package com.nchungdev.musicgo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nchungdev.musicgo.music_player.MusicPlayerState
import com.nchungdev.musicgo.repository.Song

class MusicMainViewModel : ViewModel() {

    private val _musicPlayerState = MutableLiveData<MusicPlayerState>().apply {
        value = MusicPlayerState.STOP
    }
    val musicPlayerState: MutableLiveData<MusicPlayerState> = _musicPlayerState
    val currentSong = MutableLiveData<Song>()
}