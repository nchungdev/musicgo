package com.nchungdev.musicgo.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nchungdev.musicgo.MusicMainViewModel
import com.nchungdev.musicgo.R

class LibraryFragment : Fragment() {

    private lateinit var libraryViewModel: LibraryViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        libraryViewModel = ViewModelProviders.of(this).get(LibraryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_library, container, false)
        recyclerView = root.findViewById(R.id.recyclerSong)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = activity ?: return
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val musicMainViewModel = ViewModelProviders.of(activity).get(MusicMainViewModel::class.java)
        libraryViewModel.songList.observe(this, Observer { songList ->
            recyclerView.adapter = LibraryAdapter(songList) {
                musicMainViewModel.currentSong.value = it
            }
        })
    }
}
