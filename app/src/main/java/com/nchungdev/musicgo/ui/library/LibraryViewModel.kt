package com.nchungdev.musicgo.ui.library

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nchungdev.musicgo.repository.DistinctLiveData
import com.nchungdev.musicgo.repository.Song
import com.nchungdev.musicgo.repository.SongScanner

class LibraryViewModel(application: Application) : AndroidViewModel(application) {

    private val _text = DistinctLiveData<String>().apply {
        value = "Bài Hát"
    }

    val text: LiveData<String> = _text

    val songList = MutableLiveData<List<Song>>()
}