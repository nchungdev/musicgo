package com.nchungdev.musicgo.service.notification;

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.nchungdev.musicgo.service.service.MusicService

abstract class NotificationPlayer(protected val service: MusicService) {
    protected val packageName: String = service.packageName
    private val channelId = packageName
    private val channelName = packageName
    private val notificationManager =
        service.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager

    abstract fun update()

    abstract fun toggleExpand()

    @RequiresApi(Build.VERSION_CODES.O)
    protected fun createChannelIfNeeded() {
        val channel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
        notificationManager?.createNotificationChannel(channel)
    }

    @Synchronized
    open fun stop() {
        service.stopForeground(true)
        notificationManager?.cancel(NOTIFICATION_ID)
    }

    companion object {
        const val NOTIFICATION_ID = 0x101
    }
}