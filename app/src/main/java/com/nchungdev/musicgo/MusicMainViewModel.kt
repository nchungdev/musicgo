package com.nchungdev.musicgo

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.nchungdev.musicgo.repository.DistinctLiveData
import com.nchungdev.musicgo.repository.Song
import com.nchungdev.musicgo.repository.SongScanner
import com.nchungdev.musicgo.service.MusicPlayerRemote
import com.nchungdev.musicgo.service.action.MusicConstant
import com.nchungdev.musicgo.service.service.MusicService
import com.nchungdev.musicgo.service.service.MusicServiceConnection
import com.nchungdev.musicgo.util.getActionNonNull

class MusicMainViewModel(application: Application) : AndroidViewModel(application) {
    val serviceConnectionState = DistinctLiveData<Boolean>()
    val currentPlaylist = MutableLiveData<List<Song>>()
    val currentPosition = DistinctLiveData<Int>()
    val buttonPlayPauseIcon = DistinctLiveData<Int>()
    val miniPlayerVisibility = DistinctLiveData<Boolean>()
    val nowPlayingVisibility = MutableLiveData<Boolean>()

    private var service: MusicService? = null

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent.getActionNonNull()) {
                MusicConstant.PLAY_STATE_CHANGED -> {
                    buttonPlayPauseIcon.value =
                        if (service?.isPlaying() == true) R.drawable.ic_play_24dp
                        else R.drawable.ic_pause_24dp
                    val service = service ?: return
                    when {
                        service.isStop() -> {
                            buttonPlayPauseIcon.value = R.drawable.ic_pause_24dp
                            miniPlayerVisibility.value = false
                        }
                        service.isPlaying() -> {
                            buttonPlayPauseIcon.value = R.drawable.ic_play_24dp
                            miniPlayerVisibility.value = true
                        }
                        else -> {
                            buttonPlayPauseIcon.value = R.drawable.ic_pause_24dp
                            miniPlayerVisibility.value = true
                        }
                    }
                }
                MusicConstant.META_CHANGED -> {
                    currentPosition.value = service?.getCurrentPosition()
                }
            }
        }
    }

    fun loadPlaylist() {
        currentPlaylist.postValue(SongScanner(getApplication()))
    }

    fun connectService() {
        MusicPlayerRemote.bindToService(
            getApplication(),
            MusicServiceConnection(object : MusicServiceConnection.Callback {
                override fun onConnected(service: MusicService) {
                    serviceConnectionState.value = true
                    this@MusicMainViewModel.service = service
                }

                override fun onDisconnected() {
                    serviceConnectionState.value = false
                    this@MusicMainViewModel.service = null
                }
            })
        )
        miniPlayerVisibility.value = false
    }

    fun updatePlaylistSong(list: List<Song>) {
        service?.setPlaylist(list)
    }

    fun togglePlay() {
        if (service?.isPlaying() != true) service?.play()
        else service?.pause()
    }

    fun playSongAt(position: Int) {
        service?.playAt(position)
    }

    fun playNextSong() {
        service?.nextSong()
    }

    fun playPrevSong() {
        service?.prevSong()
    }

    fun registerReceiver() {
        LocalBroadcastManager.getInstance(getApplication())
            .registerReceiver(receiver, IntentFilter().apply {
                addAction(MusicConstant.PLAY_STATE_CHANGED)
                addAction(MusicConstant.META_CHANGED)
            })
    }

    fun unregisterReceiver() {
        LocalBroadcastManager.getInstance(getApplication()).unregisterReceiver(receiver)
    }
}
