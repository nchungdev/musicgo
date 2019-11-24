package com.nchungdev.musicgo.ui.now_playing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProviders
import com.nchungdev.musicgo.R
import com.nchungdev.musicgo.repository.Song

class MainPlayingActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MainPlayingViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_now_playing)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, NowPlayingFragment.newInstance())
            .commit()

        viewModel.init(intent)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, android.R.anim.fade_out)
    }

    companion object {
        fun start(
            context: Context,
            playlist: ArrayList<Song>,
            currentSong: Int,
            optionsCompat: ActivityOptionsCompat
        ) {
            val intent = Intent(context, MainPlayingActivity::class.java).apply {
                putExtra("current_position", currentSong)
                putExtra("playlist", playlist)
            }
            context.startActivity(intent, optionsCompat.toBundle())
        }
    }
}
