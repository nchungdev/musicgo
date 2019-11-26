package com.nchungdev.musicgo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nchungdev.musicgo.custom.PlayControllerView
import com.nchungdev.musicgo.permission.PermissionUtils
import com.nchungdev.musicgo.ui.now_playing.MainPlayingActivity
import com.nchungdev.musicgo.util.setupNavigationUI
import kotlinx.android.synthetic.main.activity_music_main.*
import kotlinx.android.synthetic.main.layout_play_controller.view.*

class MusicMainActivity : AppCompatActivity(), PlayControllerView.OnPlayerController {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MusicMainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        setupNavigationUI(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        PermissionUtils.requestPermission(
            this,
            100,
            listOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        )
        mini_player.setOnControllerListener(this)

        viewModel.connectService()

        viewModel.serviceConnectionState.observe(this, Observer {
            if (it == true) {
                viewModel.loadPlaylist()
                viewModel.registerReceiver()
            }
        })
        viewModel.currentPlaylist.observe(this, Observer {
            viewModel.updatePlaylistSong(it)
        })

        viewModel.currentPosition.observe(this, Observer {
            val position = it ?: return@Observer
            viewModel.playSongAt(position)
            val playlist = viewModel.currentPlaylist.value ?: return@Observer
            mini_player.setSong(playlist[position])
        })

        viewModel.buttonPlayPauseIcon.observe(this, Observer {
            val icon = it ?: return@Observer
            mini_player.setPlayButtonIcon(icon)
        })
        viewModel.miniPlayerVisibility.observe(this, Observer {
            mini_player.isVisible = it ?: false
        })
        viewModel.nowPlayingVisibility.observe(this, Observer {
            val isVisible = it ?: return@Observer
            val currentPosition = viewModel.currentPosition.value ?: return@Observer
            val currentPlaylist = viewModel.currentPlaylist.value ?: return@Observer

            if (isVisible) {
                val animation =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this,
                        Pair(mini_player.imageCover as View, "imageCover"),
                        Pair(mini_player.controller as View, "controller")
                    )
                MainPlayingActivity.start(
                    this,
                    ArrayList(currentPlaylist),
                    currentPosition,
                    animation
                )
            }
        })
    }

    override fun onExpand() {
        viewModel.nowPlayingVisibility.postValue(true)
    }

    override fun onNext() {
        viewModel.playNextSong()
    }

    override fun onPrev() {
        viewModel.playPrevSong()
    }

    override fun onTogglePause() {
        viewModel.togglePlay()
    }

    override fun onDestroy() {
        viewModel.unregisterReceiver()
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionUtils.onRequestPermissionResult(requestCode, permissions, grantResults)
    }
}
