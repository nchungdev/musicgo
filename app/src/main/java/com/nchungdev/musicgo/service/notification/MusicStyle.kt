package com.nchungdev.musicgo.service.notification

import android.app.Notification
import android.os.Build
import androidx.core.app.NotificationBuilderWithBuilderAccessor
import androidx.core.app.NotificationCompat

class MusicStyle : NotificationCompat.Style() {

    override fun apply(builder: NotificationBuilderWithBuilderAccessor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Notification.MediaStyle().apply {
                setBuilder(builder.builder)
                setShowActionsInCompactView(0, 1, 2)
            }
        }
        super.apply(builder)
    }
}
