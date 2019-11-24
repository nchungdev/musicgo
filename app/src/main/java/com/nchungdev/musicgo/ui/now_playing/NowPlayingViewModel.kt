package com.nchungdev.musicgo.ui.now_playing

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nchungdev.musicgo.repository.DistinctLiveData
import com.nchungdev.musicgo.repository.Song
import com.nchungdev.musicgo.service.What

class NowPlayingViewModel : ViewModel() {
    val currentPlaylist = MutableLiveData<List<Song>>()
    val currentPosition = DistinctLiveData<Int>()

    fun togglePause() {
        Handler().obtainMessage(What.PLAY_SONG, 2, -1).sendToTarget()
    }

    fun nextSong() {

    }

    fun prevSong() {

    }

    fun openLyric() {

    }

    fun openPlaylist() {

    }
}
