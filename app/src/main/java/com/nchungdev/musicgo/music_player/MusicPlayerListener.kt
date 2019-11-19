package com.nchungdev.musicgo.music_player

import android.media.MediaPlayer

interface MusicPlayerListener : MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener,
    MediaPlayer.OnBufferingUpdateListener {
}