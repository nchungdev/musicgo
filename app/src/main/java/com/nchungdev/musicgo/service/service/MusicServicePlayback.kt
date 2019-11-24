package com.nchungdev.musicgo.service.service

interface MusicServicePlayback {
    fun isPlaying(): Boolean
    fun isStop(): Boolean
    fun play()
    fun pause()
    fun nextSong()
    fun prevSong()
    fun playAt(position: Int)
}
