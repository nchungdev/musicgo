package com.nchungdev.musicgo.ui.now_playing

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nchungdev.musicgo.repository.DistinctLiveData
import com.nchungdev.musicgo.repository.Song

class MainPlayingViewModel : ViewModel() {
    val currentPlaylist = MutableLiveData<List<Song>>()
    val currentPosition = DistinctLiveData<Int>()

    fun init(intent: Intent?) {
        val playlist = intent?.getParcelableArrayListExtra<Song>("playlist")
        val currentPosition = intent?.getIntExtra("current_position", -1)
        currentPlaylist.value = playlist
        this.currentPosition.value = currentPosition
    }
}
