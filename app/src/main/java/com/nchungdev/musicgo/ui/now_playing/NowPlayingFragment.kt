package com.nchungdev.musicgo.ui.now_playing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nchungdev.musicgo.R
import com.nchungdev.musicgo.custom.BigPlayControllerView
import kotlinx.android.synthetic.main.fragment_now_playing.*

class NowPlayingFragment : Fragment(), BigPlayControllerView.OnControllerListener {

    companion object {
        fun newInstance() = NowPlayingFragment()
    }

    private lateinit var viewModel: NowPlayingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_now_playing, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = activity ?: return
        viewModel = ViewModelProviders.of(this).get(NowPlayingViewModel::class.java)

        ViewModelProviders.of(activity).get(MainPlayingViewModel::class.java).run {
            currentPlaylist.observe(activity, Observer {
                viewModel.currentPlaylist.value = it
            })
            currentPosition.observe(activity, Observer {
                viewModel.currentPosition.value = it
            })
        }

        viewModel.currentPosition.observe(this, Observer {
            val song = viewModel.currentPlaylist.value?.get(it) ?: return@Observer
            textTitle.text = song.title
            textArtist.text = song.artist
            imageCover.setImageURI(song.cover)
        })
        playerControllerView.setControllerListener(this)
    }

    override fun onNext() {
        viewModel.nextSong()
    }

    override fun onPrev() {
        viewModel.prevSong()
    }

    override fun onTogglePause() {
        viewModel.togglePause()
    }

    override fun onOpenLyric() {
        viewModel.openLyric()
    }

    override fun onOpenPlaylist() {
        viewModel.openPlaylist()
    }
}
