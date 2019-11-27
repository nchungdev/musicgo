package com.nchungdev.musicgo

import android.app.Application
import android.os.StrictMode
import com.github.anrwatchdog.ANRWatchDog

@Suppress("Unused")
class MusicApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            ANRWatchDog().setReportMainThreadOnly().start()
            StrictMode.enableDefaults()
        }
    }
}
