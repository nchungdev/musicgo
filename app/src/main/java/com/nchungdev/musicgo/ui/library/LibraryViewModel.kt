package com.nchungdev.musicgo.ui.library

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nchungdev.musicgo.repository.DistinctLiveData
import com.nchungdev.musicgo.repository.Song
import com.nchungdev.musicgo.repository.SongScanner

class LibraryViewModel(application: Application) : AndroidViewModel(application) {

    private val _text = DistinctLiveData<String>().apply {
        value = "Bài Hát"
    }

    private val _songList = DistinctLiveData<List<Song>>().apply {
        value = SongScanner(getApplication())
    }

    val text: LiveData<String> = _text

    val songList : LiveData<List<Song>> = _songList
}