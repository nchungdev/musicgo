package com.nchungdev.musicgo.service.service

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder

class MusicServiceConnection(private val callback: Callback) : ServiceConnection {
    override fun onServiceDisconnected(name: ComponentName?) {
        callback.onDisconnected()
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = (service as? MusicService.GoBinder) ?: return
        callback.onConnected(binder.service)
    }

    interface Callback {
        fun onConnected(service: MusicService)
        fun onDisconnected()
    }
}
