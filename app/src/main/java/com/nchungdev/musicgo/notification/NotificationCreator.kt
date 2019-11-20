package com.nchungdev.musicgo.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.nchungdev.musicgo.R


class NotificationCreator private constructor(builder: Builder) {

    class Builder(private val context: Context) {
        private var channelId: String = context.packageName
        private var channelName: CharSequence = context.packageName
        @RequiresApi(Build.VERSION_CODES.N)
        private var importance: Int = NotificationManager.IMPORTANCE_DEFAULT
        private var contentTitle: CharSequence = context.getString(R.string.app_name)
        private var contentText: CharSequence = context.getString(R.string.app_name)
        @DrawableRes
        private var smallIcon: Int = R.drawable.ic_play_arrow_24dp
        private var largeIcon: Bitmap? = null

        private var views: RemoteViews? = null
        private var bigViews: RemoteViews? = null

        private var deleteIntent: PendingIntent? = null

        private var contentIntent: PendingIntent? = null

        @RequiresApi(Build.VERSION_CODES.N)
        fun setChannel(channelId: String, channelName: CharSequence, importance: Int) = apply {
            this.channelId = channelId
            this.channelName = channelName
            this.importance = importance
        }

        fun setContentTitle(contentTitle: CharSequence) = apply { this.contentTitle = contentTitle }

        fun setContentText(contentText: CharSequence) = apply { this.contentText = contentText }

        fun setIcon(smallIcon: Int, largeIcon: Int) = apply {
            this.smallIcon = smallIcon
            this.largeIcon = BitmapFactory.decodeResource(context.resources, largeIcon)
        }

        fun setCustomViews(remoteViews: RemoteViews) = apply { views = remoteViews }

        fun setCustomBigViews(remoteViews: RemoteViews) = apply { bigViews = remoteViews }

        fun setDeleteIntent(pendingIntent: PendingIntent) = apply { deleteIntent = pendingIntent }

        fun setContentIntent(pendingIntent: PendingIntent) = apply { contentIntent = pendingIntent }

        private fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val serviceChannel = NotificationChannel(channelId, channelName, importance)
                val manager = context.getSystemService(NotificationManager::class.java) ?: return
                manager.createNotificationChannel(serviceChannel)
            }
        }

        fun create(): Notification {

            createNotificationChannel(context)

            return NotificationCompat.Builder(context, channelId)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(smallIcon)
                .setLargeIcon(largeIcon)
                .setCustomContentView(views)
                .setCustomBigContentView(bigViews)
                .setDeleteIntent(deleteIntent)
                .setContentIntent(contentIntent)
                .build()
        }
    }
}