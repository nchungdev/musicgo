package com.nchungdev.musicgo.service.action

object MusicConstant {
    const val PACKAGE_NAME = "com.nchungdev.musicgo"
    const val ACTION_TOGGLE_PAUSE = "$PACKAGE_NAME.togglepause"
    const val ACTION_PLAY = "$PACKAGE_NAME.play"
    const val ACTION_PLAY_PLAYLIST = "$PACKAGE_NAME.play.playlist"

    const val ACTION_PAUSE = "$PACKAGE_NAME.pause"
    const val ACTION_STOP = "$PACKAGE_NAME.stop"
    const val ACTION_SKIP = "$PACKAGE_NAME.skip"
    const val ACTION_REWIND = "$PACKAGE_NAME.rewind"
    const val ACTION_QUIT = "$PACKAGE_NAME.quitservice"
    const val ACTION_PENDING_QUIT = "$PACKAGE_NAME.pendingquitservice"
    const val INTENT_EXTRA_PLAYLIST = PACKAGE_NAME + "intentextra.playlist"
    const val INTENT_EXTRA_SHUFFLE_MODE = "$PACKAGE_NAME.intentextra.shufflemode"
    const val PLAY_STATE_CHANGED = "$PACKAGE_NAME.playstatechanged"

    const val META_CHANGED = "$PACKAGE_NAME.metachanged"

    const val ACTION_TOGGLE_EXPANDED: String = "$PACKAGE_NAME.toggle_expanded"

}
