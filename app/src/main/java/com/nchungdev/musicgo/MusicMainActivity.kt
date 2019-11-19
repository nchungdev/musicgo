package com.nchungdev.musicgo

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nchungdev.musicgo.music_player.MusicPlayer
import com.nchungdev.musicgo.music_player.MusicPlayerListener
import com.nchungdev.musicgo.music_player.MusicPlayerState
import com.nchungdev.musicgo.util.PermissionUtils
import kotlinx.android.synthetic.main.activity_music_main.*

class MusicMainActivity : AppCompatActivity(), MusicPlayerListener {
    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        Log.e("Player", "onError $what")
        return false
    }

    override fun onPrepared(mp: MediaPlayer?) {
        Log.e("Player", "Prepared $mp")
        mp?.start()
        viewModel.musicPlayerState.value = MusicPlayerState.PLAY
    }

    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MusicMainViewModel::class.java)
    }
    private val musicPlayer by lazy { MusicPlayer(this@MusicMainActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        PermissionUtils.requestPermission(
            this,
            100,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        viewModel.musicPlayerState.observe(this, Observer {
            mini_player.isVisible = it.isPlayerVisible()
            if (it == MusicPlayerState.PAUSE) {
                mini_player.pause()
                musicPlayer.pause()
            } else {
                mini_player.playOrResume()
                musicPlayer.start()
            }
        })

        viewModel.currentSong.observe(this, Observer {
            val song = it ?: return@Observer
            mini_player.setSong(song)
//            startActivity(Intent(Intent.ACTION_VIEW).apply { data  = song.path })
            musicPlayer.reset()
            musicPlayer.setDataSource(this@MusicMainActivity, song.path)
            musicPlayer.prepareAsync()
        })

        mini_player.setPlayOrPauseOnClickListener {
            viewModel.musicPlayerState.value =
                if (musicPlayer.isPlaying) MusicPlayerState.PAUSE
                else MusicPlayerState.PLAY
        }
    }
}
