package com.nchungdev.musicgo.music_player

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.core.content.ContextCompat
import com.nchungdev.musicgo.repository.Song

class PlayerService : Service() {
    private val controller = PlayerController()
    private val player = MusicPlayer(controller)

    override fun onBind(intent: Intent) = PlayerServiceBinder(this, player, intent)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, NotificationPlayer(this, intent).create())
        return START_NOT_STICKY
    }

    companion object {

        fun start(context: Context, serviceConnection: ServiceConnection, song: Song) {
            val intent = Intent(context, PlayerService::class.java).apply {
                putExtra("song", song)
            }
            ContextCompat.startForegroundService(context, intent)
            context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }

        fun stop(context: Context) {
            context.stopService(Intent(context, PlayerService::class.java))
        }
    }
}
