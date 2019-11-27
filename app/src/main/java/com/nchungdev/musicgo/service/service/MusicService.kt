package com.nchungdev.musicgo.service.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.*
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.nchungdev.musicgo.repository.Song
import com.nchungdev.musicgo.service.What
import com.nchungdev.musicgo.service.action.MusicConstant
import com.nchungdev.musicgo.service.notification.NotificationPlayer
import com.nchungdev.musicgo.service.notification.NotificationPlayerImpl
import com.nchungdev.musicgo.service.playback.GoPlayer
import com.nchungdev.musicgo.service.playback.GoPlayerBinder
import com.nchungdev.musicgo.util.getActionNonNull
import java.lang.ref.WeakReference

class MusicService : Service(), MusicServiceData, MusicServicePlayback,
    MusicPlayerStateBinder,
    GoPlayerBinder.Callback {

    /**
     * Lateinit variable
     */
    private lateinit var playerBinder: GoPlayerBinder
    //    private lateinit var preferences: TrackPreferences
    private lateinit var notificationPlayer: NotificationPlayer
    private lateinit var handlerThread: HandlerThread
    private lateinit var playerHandler: PlayerHandler

    /**
     *Immutable variable
     */
    private val playingQueue = ArrayList<Song>()

    /**
     * Mutable variable
     */
    private var position = 0
    private var audioManager: AudioManager? = null
//    private lateinit var afChangeListener: AudioManager.OnAudioFocusChangeListener
//    private val myNoisyAudioStreamReceiver = BecomingNoisyReceiver()
//    private lateinit var myPlayerNotification: NotificationCompat.Style
//    private lateinit var mediaSession: MediaSession
//    private lateinit var service: MediaBrowserService
//    private lateinit var audioFocusRequest: AudioFocusRequest


    override fun onCreate() {
        super.onCreate()
        playerBinder = GoPlayer(this)
        playerBinder.setCallback(this)
//        preferences = TrackPreferences(this)
        notificationPlayer = NotificationPlayerImpl(this)
        handlerThread = HandlerThread("PlaybackHandler")
        handlerThread.start()
        playerHandler = PlayerHandler(this, handlerThread.looper)
    }

    override fun onBind(intent: Intent) = GoBinder()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent.getActionNonNull()) {
            MusicConstant.ACTION_REWIND -> prevSong()
            MusicConstant.ACTION_SKIP -> nextSong()
            MusicConstant.ACTION_TOGGLE_PAUSE -> {
                if (isPlaying()) pause()
                else play()
            }
            MusicConstant.ACTION_STOP -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    stopForeground(true)
                }
                stopSelf()
            }
        }
        return START_NOT_STICKY
    }

    override fun getCurrentPosition() = position

    override fun getNextPosition() = if (position + 1 >= playingQueue.size) 0 else position + 1

    override fun getPrevPosition() = if (position <= 0) playingQueue.size - 1 else position - 1

    override fun getSongProgressMillis() = playerBinder.position()

    override fun getSongTotalMillis() = playerBinder.duration()

    override fun setPlaylist(playlistSong: List<Song>) {
        playingQueue.clear()
        playingQueue.addAll(playlistSong)
    }

    override fun getCurrentSong() = getSongAt(getCurrentPosition())

    override fun getSongAt(position: Int) =
        if (position >= 0 && position < playingQueue.size) {
            playingQueue[position]
        } else {
            Song.EMPTY_SONG
        }

    override fun onTrackEnded() {

    }

    override fun onTrackWentToNext() {

    }

    override fun pause() {
        if (playerBinder.isPlaying()) {
            playerBinder.pause()
            notifyState(MusicConstant.PLAY_STATE_CHANGED)
        }
    }

    override fun playAt(position: Int) {
        playerHandler.removeMessages(What.PLAY_SONG)
        playerHandler.obtainMessage(What.PLAY_SONG, position, 0).sendToTarget()
    }

    fun playAtImpl(position: Int) {
        synchronized(this) {
            this.position = position
            if (openCurrent()) {
                prepareNextSongImpl()
                play()
                notifyState(MusicConstant.META_CHANGED)
            }
        }
    }

    override fun play() {
        synchronized(this) {
            if (playerBinder.isPlaying()) {
                return
            }
            if (playerBinder.isInitialized()) {
                playerBinder.start()
                notifyState(MusicConstant.PLAY_STATE_CHANGED)
            } else {
                playAt(getCurrentPosition())
            }
        }
    }

    override fun nextSong() {
        playAt(getNextPosition())
    }

    override fun prevSong() {
        playAt(getPrevPosition())
    }

    private fun openCurrent(): Boolean {
        synchronized(this) {
            return try {
                playerBinder.setDataSource(getCurrentSong().data)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    private fun prepareNextSong() {
        playerHandler.removeMessages(What.PREPARE_NEXT)
        playerHandler.obtainMessage(What.PREPARE_NEXT).sendToTarget()
    }

    fun prepareNextSongImpl() {
        synchronized(this) {
            val nextPosition = getNextPosition()
            playerBinder.setNextDataSource(getSongAt(nextPosition).data)
        }
    }

    override fun isPlaying() = playerBinder.isPlaying()

    override fun isStop() = !playerBinder.isInitialized()

    override fun notifyState(what: String) {
        handleChangeInternal(what)
        sendChangeInternal(what)
    }

    private fun sendChangeInternal(what: String) {
        LocalBroadcastManager.getInstance(this)
            .sendBroadcast(Intent(what))
        Log.e("What", "Send $what")
    }

    private fun handleChangeInternal(what: String) {
        when (what) {
            MusicConstant.PLAY_STATE_CHANGED -> {
                updateNotification()
                val isPlaying = isPlaying()
                if (!isPlaying && getSongProgressMillis() > 0) {
                    savePositionInTrack()
                }
            }
            MusicConstant.META_CHANGED -> {
                updateNotification()
            }
        }
    }

    private fun savePositionInTrack() {
//        preferences.savePositionInTrack(getSongProgressMillis())
    }

    private fun updateNotification() {
        notificationPlayer.update()
    }

    fun getAudioManager(): AudioManager {
        if (audioManager == null) {
            audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        }
        return audioManager as AudioManager
    }

    /**
     * Inner class
     */
    inner class GoBinder : Binder() {
        val service = this@MusicService
    }

    inner class PlayerHandler(service: MusicService, looper: Looper) : Handler(looper) {
        private val weakReference = WeakReference(service)

        override fun handleMessage(msg: Message) {
            val service = weakReference.get() ?: return
            super.handleMessage(msg)
            when (msg.what) {
                What.PLAY_SONG -> service.playAtImpl(msg.arg1)
                What.PREPARE_NEXT -> service.prepareNextSongImpl()
                What.SET_POSITION -> {
                }
                What.FOCUS_CHANGE -> {
                }
                What.TRACK_ENDED -> {
                }
                What.TRACK_WENT_TO_NEXT -> {
                }
            }
        }
    }
}
