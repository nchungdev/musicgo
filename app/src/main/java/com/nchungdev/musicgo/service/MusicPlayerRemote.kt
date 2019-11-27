package com.nchungdev.musicgo.service

import android.content.Context
import android.content.Intent
import android.os.Build
import com.nchungdev.musicgo.service.action.MusicConstant
import com.nchungdev.musicgo.service.service.MusicService
import com.nchungdev.musicgo.service.service.MusicServiceConnection

object MusicPlayerRemote {

    fun bindToService(context: Context, connection: MusicServiceConnection) {
        val intent = Intent(context, MusicService::class.java).apply {
            action = MusicConstant.START_SERVICE
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }
}
