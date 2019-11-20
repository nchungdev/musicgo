package com.nchungdev.musicgo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.nchungdev.musicgo.music_player.PlayerState
import com.nchungdev.musicgo.util.PermissionUtils
import kotlinx.android.synthetic.main.activity_music_main.*

class MusicMainActivity : AppCompatActivity() {

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
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        viewModel.playerState.observe(this, Observer {
            when (it) {
                PlayerState.PAUSE -> {
                    mini_player.pause()
                    mini_player.isVisible = true
                }
                PlayerState.PLAY -> {
                    mini_player.play()
                    mini_player.isVisible = true
                }
                PlayerState.STOP -> {
                    mini_player.isVisible = false
                }
                PlayerState.RESUME -> {
                    mini_player.play()
                    mini_player.isVisible = true
                }
                else -> Unit
            }
        })

        viewModel.currentSong.observe(this, Observer {
            val song = it ?: return@Observer
            mini_player.setSong(song)
            viewModel.playSong(song)
        })

        viewModel.currentPosInPlaylist.observe(this, Observer {

        })

        mini_player.setTogglePlayOnClickListener {
            viewModel.togglePlay()
        }

        mini_player.setOnNextClickListener {
            viewModel.nextSong()
        }

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(
                object : BroadcastReceiver() {
                    override fun onReceive(context: Context?, intent: Intent?) {
                        intent ?: return
                        if (intent.action == "$packageName.BROADCAST") {
                            intent.getStringExtra("action")
                        }
                    }
                },
                IntentFilter("$packageName.BROADCAST")
            )
    }
}
