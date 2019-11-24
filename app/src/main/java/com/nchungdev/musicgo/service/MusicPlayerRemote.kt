package com.nchungdev.musicgo.service

import android.content.Context
import android.content.Intent
import com.nchungdev.musicgo.service.service.MusicService
import com.nchungdev.musicgo.service.service.MusicServiceConnection

object MusicPlayerRemote {

    fun bindToService(context: Context, connection: MusicServiceConnection) {
        val intent = Intent(context, MusicService::class.java)
        context.startService(intent)
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }
}
