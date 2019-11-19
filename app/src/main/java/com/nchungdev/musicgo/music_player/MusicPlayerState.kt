package com.nchungdev.musicgo.music_player


enum class MusicPlayerState {
    PLAY, PAUSE, STOP
    ;

    fun isPlayerVisible() = this == PLAY || this == PAUSE

}
