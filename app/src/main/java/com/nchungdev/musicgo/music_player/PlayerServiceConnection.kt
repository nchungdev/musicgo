package com.nchungdev.musicgo.music_player

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder

class PlayerServiceConnection : ServiceConnection {

    var serviceBinder: PlayerServiceBinder? = null
    var isRunning = false

    override fun onServiceDisconnected(name: ComponentName?) {
        serviceBinder = null
        isRunning = false
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        serviceBinder = service as? PlayerServiceBinder ?: return
        isRunning = true
    }
}
