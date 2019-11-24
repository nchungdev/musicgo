package com.nchungdev.musicgo.service.db

import android.content.Context

class TrackPreferences(context: Context) {
    private val PREF_NAME = context.packageName + "track_state"

    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun savePositionInTrack(position: Int) {
        sharedPreferences.edit()
            .putInt(Key.Track.POSITION_IN_TRACK.name, position)
            .apply()
    }

    fun getPositionInTrack(): Int = sharedPreferences.getInt(Key.Track.POSITION_IN_TRACK.name, 0)

    interface Key {
        enum class Track {
            POSITION_IN_TRACK
        }
    }
}