package com.nchungdev.musicgo.service.service

import com.nchungdev.musicgo.repository.Song

interface MusicServiceData {
    fun setPlaylist(playlistSong: List<Song>)
    fun getCurrentSong(): Song
    fun getSongAt(position: Int): Song
    fun getCurrentPosition(): Int
    fun getNextPosition(): Int
    fun getPrevPosition(): Int
    fun getSongProgressMillis(): Int
    fun getSongTotalMillis(): Int
}
