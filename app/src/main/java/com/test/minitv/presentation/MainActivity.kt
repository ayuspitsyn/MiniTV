package com.test.minitv.presentation

import android.content.res.AssetFileDescriptor
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.test.minitv.MiniTvApp
import com.test.minitv.R

class MainActivity : AppCompatActivity(), SurfaceHolder.Callback, MediaPlayer.OnPreparedListener,
    MediaPlayer.OnCompletionListener {

    private val miniTvViewModel: MiniTvViewModel by viewModels {
        MiniTvViewModelFactory(
            (application as MiniTvApp).database.miniTvDao(),
            assets
        )
    }

    private lateinit var surfaceView: SurfaceView
    private lateinit var player: MediaPlayer
    private lateinit var surfaceHolder: SurfaceHolder
    private lateinit var videoSource: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        surfaceView = findViewById(R.id.surfaceView)
        surfaceHolder = surfaceView.holder
        surfaceHolder.addCallback(this@MainActivity)

        miniTvViewModel.videoSource.observe(this@MainActivity) { videoSource = it }
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        player = MediaPlayer()
        player.setDisplay(surfaceHolder)
        try {
            setupPlayer()
        } catch (e: java.lang.Exception) {
            Log.e("LOG_TAG", "Error=" + e)
        }

    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {}

    override fun surfaceDestroyed(p0: SurfaceHolder) {}

    private fun setupPlayer() {
        val afd: AssetFileDescriptor = assets.openFd(videoSource)
        player.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        player.prepare()
        player.setOnPreparedListener(this@MainActivity)
        player.setOnCompletionListener(this@MainActivity)
        player.setAudioAttributes(
            AudioAttributes
                .Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )
    }

    override fun onPrepared(p0: MediaPlayer?) {
        player.start()
    }

    override fun onCompletion(mp: MediaPlayer?) {
        Toast.makeText(this, "Next", Toast.LENGTH_SHORT).show()
        miniTvViewModel.getNext()
        if (mp == null) {
            player = MediaPlayer()
        } else {
            player.reset()
        }
        try {
            setupPlayer()
        } catch (e: java.lang.Exception) {
            Log.e("LOG_TAG", "Error=" + e)
        }
    }

    override fun onPause() {
        super.onPause()
        stopPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopPlayer()
    }

    private fun stopPlayer() {
        if (::player.isInitialized) {
            player.release()
        }
    }
}