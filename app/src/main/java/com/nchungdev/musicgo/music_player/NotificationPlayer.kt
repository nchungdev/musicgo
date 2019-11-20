package com.nchungdev.musicgo.music_player

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import com.nchungdev.musicgo.MusicMainActivity
import com.nchungdev.musicgo.R
import com.nchungdev.musicgo.notification.NotificationCreator
import com.nchungdev.musicgo.repository.Song

class NotificationPlayer(private val context: Context, intent: Intent?) {
    private val packageName = context.packageName
    private val song = intent?.getParcelableExtra<Song>("song")

    fun create(): Notification {
        val build = NotificationCreator.Builder(context).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                setChannel(packageName, packageName, NotificationManager.IMPORTANCE_LOW)
            }
            setIcon(R.drawable.ic_notification_small, R.drawable.ic_app_64)
            setContentIntent(createContentIntent())
            setCustomViews(createCustomViews())
        }
        return build.create()
    }


    private fun createContentIntent(): PendingIntent {
        val intent = Intent(context, MusicMainActivity::class.java)
        return PendingIntent.getActivity(context, 0, intent, 0)
    }

    private fun createCustomViews() =
        RemoteViews(context.packageName, R.layout.notification_view).apply {
            setImageViewResource(R.id.btnPlay, R.drawable.ic_play_arrow_24dp)
            setImageViewResource(R.id.btnNext, R.drawable.ic_skip_next_24dp)
            setImageViewUri(R.id.imageCover, song?.cover)
            setTextViewText(R.id.textTitle, song?.title)
            setTextViewText(R.id.textArtist, song?.artist)

            val intentNext = Intent("$packageName.BROADCAST").apply {
                putExtra("action", "Next")
            }
            val intentPlay = Intent("$packageName.BROADCAST").apply {
                putExtra("action", "Play")
            }
            val pendingIntentNext = PendingIntent.getBroadcast(context, 1, intentNext, 0);
            val pendingIntentPlay = PendingIntent.getBroadcast(context, 3, intentPlay, 0);

            setOnClickPendingIntent(R.id.btnPlay, pendingIntentPlay)
            setOnClickPendingIntent(R.id.btnNext, pendingIntentNext)
        }
}
