package com.nchungdev.musicgo.service.notification

import android.content.Context
import androidx.core.app.NotificationCompat
import com.nchungdev.musicgo.R
import com.nchungdev.musicgo.service.action.MusicConstant
import com.nchungdev.musicgo.service.service.MusicService

class AddingNotificationAction(private val builder: NotificationCompat.Builder) {

    fun invoke(service: MusicService): NotificationCompat.Builder {
        addAction(service, R.drawable.ic_skip_previous_24dp, "Prev", MusicConstant.ACTION_REWIND)
        addAction(
            service,
            if (service.isPlaying()) R.drawable.ic_play_24dp else R.drawable.ic_pause_24dp,
            "TogglePause",
            MusicConstant.ACTION_TOGGLE_PAUSE
        )
        addAction(service, R.drawable.ic_skip_next_24dp, "Next", MusicConstant.ACTION_REWIND)
        return builder
    }

    private fun addAction(context: Context, icon: Int, title: CharSequence, action: String) {
        builder.addAction(
            icon,
            title,
            MediaButtonReceiver.buildMediaButtonPendingIntent(
                context,
                action
            )
        )
    }
}