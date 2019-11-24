package com.nchungdev.musicgo.service.notification

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.nchungdev.musicgo.service.service.MusicService

object MediaButtonReceiver {
    fun buildMediaButtonPendingIntent(context: Context, action: String): PendingIntent =
        PendingIntent.getService(
            context,
            0,
            Intent(action).apply { component = ComponentName(context, MusicService::class.java) },
            0
        )
}
