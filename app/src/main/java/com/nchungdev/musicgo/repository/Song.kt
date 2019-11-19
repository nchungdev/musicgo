package com.nchungdev.musicgo.repository

import android.net.Uri

class Song(
    val title: String,
    val album: String,
    val artist: String,
    val cover: Uri = Uri.EMPTY,
    val path: Uri = Uri.EMPTY
)