package com.nchungdev.musicgo.service.notification

import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.nchungdev.musicgo.MusicMainActivity
import com.nchungdev.musicgo.R
import com.nchungdev.musicgo.service.action.MusicConstant
import com.nchungdev.musicgo.service.service.MusicService
import com.nchungdev.musicgo.util.Uri2Bitmap


class NotificationPlayerImpl(service: MusicService) : NotificationPlayer(service) {

    private fun createNotification(): NotificationCompat.Builder {
        return NotificationCompat.Builder(service, packageName).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createChannelIfNeeded()
            }
            val song = service.getCurrentSong()
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            setSmallIcon(R.drawable.ic_notification_small)
            setLargeIcon(Uri2Bitmap.getThumbnail(service, song.cover, R.drawable.ic_play_24dp))
            setContentIntent(createContentIntent())
            setStyle(MusicStyle())
            setContentTitle(song.title)
            setContentText(song.artist)
            setOngoing(service.isPlaying())
            setDeleteIntent(
                PendingIntent.getService(
                    service,
                    0,
                    Intent(service, MusicService::class.java).apply {
                        action = MusicConstant.ACTION_STOP
                    },
                    PendingIntent.FLAG_CANCEL_CURRENT
                )
            )
            AddingNotificationAction(this).invoke(service)
        }
    }

    private fun createContentIntent(): PendingIntent {
        val intent = Intent(service, MusicMainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        return PendingIntent.getActivity(service, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    override fun update() {
        val builder = createNotification()
//        val mn = service.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        mn.notify(NOTIFICATION_ID, builder.build())
        service.startForeground(NOTIFICATION_ID, builder.build())
    }

    override fun toggleExpand() {

    }
}
