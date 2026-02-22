/*
 * VideoPlayerActivity - 使用 Media3 ExoPlayer 播放视频
 */

package com.douyin.tv

import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class VideoPlayerActivity : ComponentActivity() {

    companion object {
        const val EXTRA_VIDEO_URL = "extra_video_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val videoUrl = intent.getStringExtra(EXTRA_VIDEO_URL) ?: run {
            finish()
            return
        }

        setContent {
            val viewModel: PlayerViewModel = viewModel()

            DisposableEffect(Unit) {
                viewModel.initializePlayer(videoUrl)
                onDispose {
                    viewModel.releasePlayer()
                }
            }

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Black
            ) {
                VideoPlayerScreen(
                    player = viewModel.player,
                    isLoading = viewModel.isLoading.value,
                    errorMessage = viewModel.errorMessage.value
                )
            }
        }
    }

    /**
     * 处理遥控器按键
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_DPAD_UP,
            KeyEvent.KEYCODE_DPAD_DOWN,
            KeyEvent.KEYCODE_DPAD_LEFT,
            KeyEvent.KEYCODE_DPAD_RIGHT,
            KeyEvent.KEYCODE_DPAD_CENTER,
            KeyEvent.KEYCODE_ENTER,
            KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE,
            KeyEvent.KEYCODE_MEDIA_PLAY,
            KeyEvent.KEYCODE_MEDIA_PAUSE -> {
                return handleMediaKey(keyCode)
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun handleMediaKey(keyCode: Int): Boolean {
        val viewModel = PlayerViewModel()
        when (keyCode) {
            KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE -> {
                viewModel.player?.let {
                    if (it.isPlaying) it.pause() else it.play()
                }
                return true
            }
            KeyEvent.KEYCODE_MEDIA_PLAY -> {
                viewModel.player?.play()
                return true
            }
            KeyEvent.KEYCODE_MEDIA_PAUSE -> {
                viewModel.player?.pause()
                return true
            }
            KeyEvent.KEYCODE_DPAD_LEFT -> {
                viewModel.player?.seekBack()
                return true
            }
            KeyEvent.KEYCODE_DPAD_RIGHT -> {
                viewModel.player?.seekForward()
                return true
            }
            KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                viewModel.player?.let {
                    if (it.isPlaying) it.pause() else it.play()
                }
                return true
            }
        }
        return false
    }
}

class PlayerViewModel : ViewModel() {

    var player: ExoPlayer? = null
        private set

    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    /**
     * 初始化播放器
     */
    fun initializePlayer(videoUrl: String) {
        isLoading.value = true
        errorMessage.value = null

        player = ExoPlayer.Builder(
            GlobalContext.getContext(),
            DefaultRenderersFactoryProvider.getRenderersFactory()
        ).build().apply {
            val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
            setMediaItem(mediaItem)
            prepare()

            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(@Player.State playbackState: Int) {
                    when (playbackState) {
                        Player.STATE_IDLE -> {
                            isLoading.value = false
                        }
                        Player.STATE_BUFFERING -> {
                            isLoading.value = true
                        }
                        Player.STATE_READY -> {
                            isLoading.value = false
                        }
                        Player.STATE_ENDED -> {
                            isLoading.value = false
                        }
                    }
                }

                override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                    isLoading.value = false
                    errorMessage.value = "播放失败: ${error.message}"
                }
            })

            play()
        }
    }

    /**
     * 释放播放器
     */
    fun releasePlayer() {
        player?.run {
            stop()
            release()
        }
        player = null
    }
}

/**
 * 视频播放界面
 */
@Composable
fun VideoPlayerScreen(
    player: ExoPlayer?,
    isLoading: Boolean,
    errorMessage: String?
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        if (player != null) {
            AndroidView(
                factory = { ctx ->
                    PlayerView(ctx).apply {
                        this.player = player
                        useController = true
                        resizeMode = androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_FIT
                        setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        errorMessage?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
